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
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.exceptions.base.MockitoException;

import com.github.dockerjava.api.DockerClient;

/**
 * Ensures that every method in DelegatingDockerClient delegates to the matching
 * method in the instance it delegates to. Uses reflection/introspection to
 * ensure that this class doesn't need updating if new methods are added.
 */
@RunWith(Parameterized.class)
public class DelegatingDockerClientTest {

    /**
     * Defines the set of data that all test methods (in this test class) will be
     * run with - in this case, it's all the methods of {@link DockerClient}.
     * <p>
     * Each element in the returned {@link Iterable} is an Object[] whose contents
     * matches the arguments taken by this class's constructor.
     * </p>
     * The annotation <code>name = "{0}"</code> says that the name of each set of
     * data should be first element of the array.
     * 
     * @return {@link Iterable} of [ {@link String}, {@link Method} ].
     */
    @Parameterized.Parameters(name = "{0}")
    public static Iterable<Object[]> data() {
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
            final Object[] testCase = new Object[] { testCaseName.toString(), m };
            data.add(testCase);
        }
        data.sort(new Comparator<Object[]>() {
            @Override
            public int compare(Object[] o1, Object[] o2) {
                final String n1 = (String) o1[0];
                final String n2 = (String) o2[0];
                return n1.compareTo(n2);
            }
        });
        return data;
    }

    private final String dockerClientMethodName;
    private final Method dockerClientMethod;

    public DelegatingDockerClientTest(String methodName, Method dockerClientMethod) {
        this.dockerClientMethodName = methodName;
        this.dockerClientMethod = dockerClientMethod;
    }

    @Test
    public void methodIsDelegatedCorrectly() throws Exception {
        // Given
        final Parameter[] methodParameters = dockerClientMethod.getParameters();
        final Object[] mockParameters = createFakeArgumentValues(methodParameters);
        final Class<?> methodReturnType = dockerClientMethod.getReturnType();
        final Object mockReturnValue = methodReturnType.equals(Void.TYPE) ? null
                : createFakeObject(methodReturnType, dockerClientMethodName + "ReturnedValue");
        final DockerClient mockDelegate = mock(DockerClient.class, "mockDelegate");
        if (mockReturnValue != null) {
            when(dockerClientMethod.invoke(mockDelegate, mockParameters)).thenReturn(mockReturnValue);
        }
        final DelegatingDockerClient instanceUnderTest = new DelegatingDockerClient(mockDelegate);

        // When
        final Object actualReturnValue = dockerClientMethod.invoke(instanceUnderTest, mockParameters);

        // Then
        if (mockReturnValue != null) {
            assertThat("Returned value is what delegate returned", actualReturnValue, sameInstance(mockReturnValue));
        }
        dockerClientMethod.invoke(verify(mockDelegate, times(1)), mockParameters);
        verifyNoMoreInteractions(mockDelegate);
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
            // If, in future, this isn't enough then we could try picking another
            // constructor and trying to fake any arguments it needs.
            final Constructor<?> defaultConstructor = paramType.getConstructor();
            return defaultConstructor.newInstance();
        }
    }
}
