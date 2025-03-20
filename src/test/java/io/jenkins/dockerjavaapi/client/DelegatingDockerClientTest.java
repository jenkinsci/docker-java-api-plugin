// Copyright 2011 Peter Darton
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to
// deal in the Software without restriction, including without limitation the
// rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
// sell copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
// FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
// IN THE SOFTWARE.

package io.jenkins.dockerjavaapi.client;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import com.github.dockerjava.api.DockerClient;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InOrder;
import org.mockito.exceptions.base.MockitoException;

/**
 * Ensures that every method in DelegatingDockerClient delegates to the matching
 * method in the instance it delegates to. Uses reflection/introspection to
 * ensure that this class doesn't need updating if new methods are added.
 */
class DelegatingDockerClientTest {

    /**
     * Defines the set of data that all test methods (in this test class) will be
     * run with - in this case, it's all the methods of {@link DockerClient}.
     * <p>
     * Each element in the returned {@link Iterable} is an Object[] whose contents
     * matches the arguments taken by this class's constructor.
     * </p>
     * Note: The annotation <code>name = "{0}"</code> says that the name of each set of
     * data should be first element of the array.
     *
     * @return {@link Iterable} of [ {@link String}, {@link Method} ].
     */
    static Iterable<Object[]> data() {
        final List<Object[]> data = new ArrayList<>();
        final Method[] declaredMethods = DockerClient.class.getDeclaredMethods();
        for (Method m : declaredMethods) {
            final StringBuilder testCaseName = new StringBuilder(m.getName());
            testCaseName.append('(');
            for (Class<?> t : m.getParameterTypes()) {
                final String tName = t.getSimpleName();
                if (testCaseName.charAt(testCaseName.length() - 1) != '(') {
                    testCaseName.append(',');
                }
                testCaseName.append(tName);
            }
            testCaseName.append(')');
            final Object[] testCase = new Object[] {testCaseName.toString(), m};
            data.add(testCase);
        }
        data.sort((o1, o2) -> {
            final String n1 = (String) o1[0];
            final String n2 = (String) o2[0];
            return n1.compareTo(n2);
        });
        return data;
    }

    private interface HookPoints {
        void interceptAnswerCalled(Object originalAnswer);

        void interceptVoidCalled();
    }

    private static class DelegatingDockerClientUnderTest extends DelegatingDockerClient {
        final HookPoints hooks = mock(HookPoints.class);

        protected DelegatingDockerClientUnderTest(DockerClient delegate) {
            super(delegate);
        }

        @Override
        protected <T> T interceptAnswer(T originalAnswer) {
            hooks.interceptAnswerCalled(originalAnswer);
            return super.interceptAnswer(originalAnswer);
        }

        @Override
        protected void interceptVoid() {
            hooks.interceptVoidCalled();
            super.interceptVoid();
        }
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("data")
    void methodIsDelegatedCorrectly(String dockerClientMethodName, Method dockerClientMethod) throws Exception {
        // Given
        final Parameter[] methodParameters = dockerClientMethod.getParameters();
        final Object[] mockParameters = createFakeArgumentValues(methodParameters);
        final Class<?> methodReturnType = dockerClientMethod.getReturnType();
        final Object mockReturnValue = methodReturnType.equals(Void.TYPE)
                ? null
                : createFakeObject(methodReturnType, dockerClientMethodName + "ReturnedValue");
        final DockerClient mockDelegate = mock(DockerClient.class, "mockDelegate");
        if (mockReturnValue != null) {
            when(dockerClientMethod.invoke(mockDelegate, mockParameters)).thenReturn(mockReturnValue);
        }
        final DelegatingDockerClientUnderTest instanceUnderTest = new DelegatingDockerClientUnderTest(mockDelegate);

        // When
        final Object actualReturnValue = dockerClientMethod.invoke(instanceUnderTest, mockParameters);

        // Then
        if (mockReturnValue != null) {
            assertThat("Returned value is what delegate returned", actualReturnValue, sameInstance(mockReturnValue));
        }
        final InOrder inOrder = inOrder(mockDelegate, instanceUnderTest.hooks);
        dockerClientMethod.invoke(inOrder.verify(mockDelegate, times(1)), mockParameters);
        if (mockReturnValue != null) {
            inOrder.verify(instanceUnderTest.hooks).interceptAnswerCalled(actualReturnValue);
        } else {
            inOrder.verify(instanceUnderTest.hooks).interceptVoidCalled();
        }
        inOrder.verifyNoMoreInteractions();
    }

    private static Object[] createFakeArgumentValues(final Parameter[] methodParameters) throws Exception {
        final Object[] mockParameters = new Object[methodParameters.length];
        for (int i = 0; i < methodParameters.length; i++) {
            final Class<?> paramType = methodParameters[i].getType();
            final String paramName = "arg" + i;
            mockParameters[i] = createFakeObject(paramType, paramName);
        }
        return mockParameters;
    }

    private static Object createFakeObject(final Class<?> paramType, final String paramName) throws Exception {
        if (paramType.isEnum()) {
            final Object[] values = (Object[]) paramType.getMethod("values").invoke(null);
            return values[values.length / 2]; // pick a value in the middle
        }
        try {
            return mock(paramType, paramName);
        } catch (MockitoException ex) {
            // Many arguments are of final classes that mockito can't mock, causing this
            // exception.
            // In such cases we just have to hope there's a constructor we
            // can access and make a real instance instead of a mock.
            // MAINTENANCE NOTE:
            // So far we've gotten away with only looking for a default constructor.
            // If, in the future, this isn't enough then we could try picking another
            // constructor and trying to fake any arguments it needs.
            final Constructor<?> defaultConstructor = paramType.getConstructor();
            return defaultConstructor.newInstance();
        }
    }
}
