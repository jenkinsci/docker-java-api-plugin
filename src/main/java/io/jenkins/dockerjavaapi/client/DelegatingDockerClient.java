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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import edu.umd.cs.findbugs.annotations.NonNull;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.exception.DockerException;
import com.github.dockerjava.api.model.*;

// MAINTENANCE NOTE:
// The DockerClient API varies depending on the version of the docker-java
// library that this plugin provides.
// It may be necessary to add/remove methods from this class when changing
// the version of docker-java.
// ...but, by having this here, it allows all other Jenkins plugins to be much
// less affected by changes to the version of docker-java, meaning that this
// plugin can be upgraded without breaking every other docker-using plugin.

/**
 * Simple delegate class for the {@link DockerClient} interface.
 * <p>
 * This makes it easy for other classes to override specific methods without
 * having to implement all of them. Every method here:
 * <ul>
 * <li>calls {@link #getDelegate()},</li>
 * <li>calls the matching method on the delegate class,</li>
 * <li>for methods that return void, calls {@link #interceptVoid()} and then
 * returns,</li>
 * <li>for other methods, calls {@link #interceptAnswer(Object)}, passing in the
 * delegate method's answer and returns whatever
 * {@link #interceptAnswer(Object)} returned.</li>
 * </ul>
 * <p>
 * If you are writing a Jenkins plugin that needs a class to implement/wrap
 * {@link DockerClient}, you'd be best advised to extend this one, otherwise
 * your code could fail whenever the version of this plugin changes and the
 * {@link DockerClient} gains additional methods.
 */
@SuppressWarnings("deprecation")
public class DelegatingDockerClient implements DockerClient {

    private final DockerClient delegate;

    /**
     * Constructs a new instance that delegates all API calls to the specified
     * {@link DockerClient}.
     *
     * @param delegate The {@link DockerClient} to delegate to.
     */
    public DelegatingDockerClient(@NonNull DockerClient delegate) {
        this.delegate = delegate;
    }

    /**
     * Obtains the underlying {@link DockerClient} interface. Subclasses can
     * override this if they need to hook into every call before anything else
     * happens.
     *
     * @return the {@link DockerClient} to be delegated to.
     */
    @NonNull
    protected DockerClient getDelegate() {
        return delegate;
    }

    /**
     * Called just before the result is returned. Subclasses can override this if
     * they need to hook into every call just before the method returns and/or to
     * alter the result.
     * <p>
     * Note: If a subclass only wishes to act upon certain specific
     * {@link DockerClient} calls then it may be clearer to override those specific
     * methods instead. This hook is intended for use by subclasses that need to act
     * upon "all methods" or need to act on methods that were not part of the
     * {@link DockerClient} API at the time they were implemented.
     * 
     * @param originalAnswer The result from the delegate.
     * @param                <T> The type of the <code>originalAnswer</code>.
     * @return The result to be returned instead.
     */
    protected <T> T interceptAnswer(T originalAnswer) {
        return originalAnswer;
    }

    /**
     * Called just before the method returns void. Allows a subclass to act just
     * before the method returns.
     * <p>
     * Note: If a subclass only wishes to act upon certain specific
     * {@link DockerClient} calls then it may be clearer to override those specific
     * methods instead. This hook is intended for use by subclasses that need to act
     * upon "all methods" or need to act on methods that were not part of the
     * {@link DockerClient} API at the time they were implemented.
     */
    protected void interceptVoid() {
    }

    @Override
    public AttachContainerCmd attachContainerCmd(String arg0) {
        return interceptAnswer(getDelegate().attachContainerCmd(arg0));
    }

    @Override
    public AuthCmd authCmd() {
        return interceptAnswer(getDelegate().authCmd());
    }

    @Override
    public AuthConfig authConfig() throws DockerException {
        return interceptAnswer(getDelegate().authConfig());
    }

    @Override
    public BuildImageCmd buildImageCmd() {
        return interceptAnswer(getDelegate().buildImageCmd());
    }

    @Override
    public BuildImageCmd buildImageCmd(File arg0) {
        return interceptAnswer(getDelegate().buildImageCmd(arg0));
    }

    @Override
    public BuildImageCmd buildImageCmd(InputStream arg0) {
        return interceptAnswer(getDelegate().buildImageCmd(arg0));
    }

    @Override
    public void close() throws IOException {
        getDelegate().close();
        interceptVoid();
    }

    @Override
    public CommitCmd commitCmd(String arg0) {
        return interceptAnswer(getDelegate().commitCmd(arg0));
    }

    @Override
    public ConnectToNetworkCmd connectToNetworkCmd() {
        return interceptAnswer(getDelegate().connectToNetworkCmd());
    }

    @Override
    public ContainerDiffCmd containerDiffCmd(String arg0) {
        return interceptAnswer(getDelegate().containerDiffCmd(arg0));
    }

    @Override
    public CopyArchiveFromContainerCmd copyArchiveFromContainerCmd(String arg0, String arg1) {
        return interceptAnswer(getDelegate().copyArchiveFromContainerCmd(arg0, arg1));
    }

    @Override
    public CopyArchiveToContainerCmd copyArchiveToContainerCmd(String arg0) {
        return interceptAnswer(getDelegate().copyArchiveToContainerCmd(arg0));
    }

    @Override
    public CopyFileFromContainerCmd copyFileFromContainerCmd(String arg0, String arg1) {
        return interceptAnswer(getDelegate().copyFileFromContainerCmd(arg0, arg1));
    }

    @Override
    public CreateContainerCmd createContainerCmd(String arg0) {
        return interceptAnswer(getDelegate().createContainerCmd(arg0));
    }

    @Override
    public CreateImageCmd createImageCmd(String arg0, InputStream arg1) {
        return interceptAnswer(getDelegate().createImageCmd(arg0, arg1));
    }

    @Override
    public CreateNetworkCmd createNetworkCmd() {
        return interceptAnswer(getDelegate().createNetworkCmd());
    }

    @Override
    public CreateVolumeCmd createVolumeCmd() {
        return interceptAnswer(getDelegate().createVolumeCmd());
    }

    @Override
    public DisconnectFromNetworkCmd disconnectFromNetworkCmd() {
        return interceptAnswer(getDelegate().disconnectFromNetworkCmd());
    }

    @Override
    public EventsCmd eventsCmd() {
        return interceptAnswer(getDelegate().eventsCmd());
    }

    @Override
    public ExecCreateCmd execCreateCmd(String arg0) {
        return interceptAnswer(getDelegate().execCreateCmd(arg0));
    }

    @Override
    public ExecStartCmd execStartCmd(String arg0) {
        return interceptAnswer(getDelegate().execStartCmd(arg0));
    }

    @Override
    public InfoCmd infoCmd() {
        return interceptAnswer(getDelegate().infoCmd());
    }

    @Override
    public InspectContainerCmd inspectContainerCmd(String arg0) {
        return interceptAnswer(getDelegate().inspectContainerCmd(arg0));
    }

    @Override
    public InspectExecCmd inspectExecCmd(String arg0) {
        return interceptAnswer(getDelegate().inspectExecCmd(arg0));
    }

    @Override
    public InspectImageCmd inspectImageCmd(String arg0) {
        return interceptAnswer(getDelegate().inspectImageCmd(arg0));
    }

    @Override
    public InspectNetworkCmd inspectNetworkCmd() {
        return interceptAnswer(getDelegate().inspectNetworkCmd());
    }

    @Override
    public InspectVolumeCmd inspectVolumeCmd(String arg0) {
        return interceptAnswer(getDelegate().inspectVolumeCmd(arg0));
    }

    @Override
    public KillContainerCmd killContainerCmd(String arg0) {
        return interceptAnswer(getDelegate().killContainerCmd(arg0));
    }

    @Override
    public ListContainersCmd listContainersCmd() {
        return interceptAnswer(getDelegate().listContainersCmd());
    }

    @Override
    public ListImagesCmd listImagesCmd() {
        return interceptAnswer(getDelegate().listImagesCmd());
    }

    @Override
    public ListNetworksCmd listNetworksCmd() {
        return interceptAnswer(getDelegate().listNetworksCmd());
    }

    @Override
    public ListVolumesCmd listVolumesCmd() {
        return interceptAnswer(getDelegate().listVolumesCmd());
    }

    @Override
    public LoadImageCmd loadImageCmd(InputStream arg0) {
        return interceptAnswer(getDelegate().loadImageCmd(arg0));
    }

    @Override
    public LogContainerCmd logContainerCmd(String arg0) {
        return interceptAnswer(getDelegate().logContainerCmd(arg0));
    }

    @Override
    public PauseContainerCmd pauseContainerCmd(String arg0) {
        return interceptAnswer(getDelegate().pauseContainerCmd(arg0));
    }

    @Override
    public PingCmd pingCmd() {
        return interceptAnswer(getDelegate().pingCmd());
    }

    @Override
    public PullImageCmd pullImageCmd(String arg0) {
        return interceptAnswer(getDelegate().pullImageCmd(arg0));
    }

    @Override
    public PushImageCmd pushImageCmd(String arg0) {
        return interceptAnswer(getDelegate().pushImageCmd(arg0));
    }

    @Override
    public PushImageCmd pushImageCmd(Identifier arg0) {
        return interceptAnswer(getDelegate().pushImageCmd(arg0));
    }

    @Override
    public RemoveContainerCmd removeContainerCmd(String arg0) {
        return interceptAnswer(getDelegate().removeContainerCmd(arg0));
    }

    @Override
    public RemoveImageCmd removeImageCmd(String arg0) {
        return interceptAnswer(getDelegate().removeImageCmd(arg0));
    }

    @Override
    public RemoveNetworkCmd removeNetworkCmd(String arg0) {
        return interceptAnswer(getDelegate().removeNetworkCmd(arg0));
    }

    @Override
    public RemoveVolumeCmd removeVolumeCmd(String arg0) {
        return interceptAnswer(getDelegate().removeVolumeCmd(arg0));
    }

    @Override
    public RenameContainerCmd renameContainerCmd(String arg0) {
        return interceptAnswer(getDelegate().renameContainerCmd(arg0));
    }

    @Override
    public RestartContainerCmd restartContainerCmd(String arg0) {
        return interceptAnswer(getDelegate().restartContainerCmd(arg0));
    }

    @Override
    public SaveImageCmd saveImageCmd(String arg0) {
        return interceptAnswer(getDelegate().saveImageCmd(arg0));
    }

    @Override
    public SearchImagesCmd searchImagesCmd(String arg0) {
        return interceptAnswer(getDelegate().searchImagesCmd(arg0));
    }

    @Override
    public StartContainerCmd startContainerCmd(String arg0) {
        return interceptAnswer(getDelegate().startContainerCmd(arg0));
    }

    @Override
    public StatsCmd statsCmd(String arg0) {
        return interceptAnswer(getDelegate().statsCmd(arg0));
    }

    @Override
    public StopContainerCmd stopContainerCmd(String arg0) {
        return interceptAnswer(getDelegate().stopContainerCmd(arg0));
    }

    @Override
    public TagImageCmd tagImageCmd(String arg0, String arg1, String arg2) {
        return interceptAnswer(getDelegate().tagImageCmd(arg0, arg1, arg2));
    }

    @Override
    public TopContainerCmd topContainerCmd(String arg0) {
        return interceptAnswer(getDelegate().topContainerCmd(arg0));
    }

    @Override
    public UnpauseContainerCmd unpauseContainerCmd(String arg0) {
        return interceptAnswer(getDelegate().unpauseContainerCmd(arg0));
    }

    @Override
    public UpdateContainerCmd updateContainerCmd(String arg0) {
        return interceptAnswer(getDelegate().updateContainerCmd(arg0));
    }

    @Override
    public VersionCmd versionCmd() {
        return interceptAnswer(getDelegate().versionCmd());
    }

    @Override
    public WaitContainerCmd waitContainerCmd(String arg0) {
        return interceptAnswer(getDelegate().waitContainerCmd(arg0));
    }

    @Override
    public InitializeSwarmCmd initializeSwarmCmd(SwarmSpec swarmSpec) {
        return interceptAnswer(getDelegate().initializeSwarmCmd(swarmSpec));
    }

    @Override
    public InspectSwarmCmd inspectSwarmCmd() {
        return interceptAnswer(getDelegate().inspectSwarmCmd());
    }

    @Override
    public JoinSwarmCmd joinSwarmCmd() {
        return interceptAnswer(getDelegate().joinSwarmCmd());
    }

    @Override
    public LeaveSwarmCmd leaveSwarmCmd() {
        return interceptAnswer(getDelegate().leaveSwarmCmd());
    }

    @Override
    public UpdateSwarmCmd updateSwarmCmd(SwarmSpec swarmSpec) {
        return interceptAnswer(getDelegate().updateSwarmCmd(swarmSpec));
    }

    @Override
    public UpdateSwarmNodeCmd updateSwarmNodeCmd() {
        return interceptAnswer(getDelegate().updateSwarmNodeCmd());
    }

    @Override
    public ListSwarmNodesCmd listSwarmNodesCmd() {
        return interceptAnswer(getDelegate().listSwarmNodesCmd());
    }

    @Override
    public ListServicesCmd listServicesCmd() {
        return interceptAnswer(getDelegate().listServicesCmd());
    }

    @Override
    public CreateServiceCmd createServiceCmd(ServiceSpec serviceSpec) {
        return interceptAnswer(getDelegate().createServiceCmd(serviceSpec));
    }

    @Override
    public InspectServiceCmd inspectServiceCmd(String serviceId) {
        return interceptAnswer(getDelegate().inspectServiceCmd(serviceId));
    }

    @Override
    public UpdateServiceCmd updateServiceCmd(String serviceId, ServiceSpec serviceSpec) {
        return interceptAnswer(getDelegate().updateServiceCmd(serviceId, serviceSpec));
    }

    @Override
    public RemoveServiceCmd removeServiceCmd(String serviceId) {
        return interceptAnswer(getDelegate().removeServiceCmd(serviceId));
    }

    @Override
    public ListTasksCmd listTasksCmd() {
        return interceptAnswer(getDelegate().listTasksCmd());
    }

    @Override
    public LogSwarmObjectCmd logServiceCmd(String serviceId) {
        return interceptAnswer(getDelegate().logServiceCmd(serviceId));
    }

    @Override
    public LogSwarmObjectCmd logTaskCmd(String taskId) {
        return interceptAnswer(getDelegate().logTaskCmd(taskId));
    }

    @Override
    public PruneCmd pruneCmd(PruneType pruneType) {
        return interceptAnswer(getDelegate().pruneCmd(pruneType));
    }

    @Override
    public RemoveConfigCmd removeConfigCmd(String configId) {
        return interceptAnswer(getDelegate().removeConfigCmd(configId));
    }

    @Override
    public InspectConfigCmd inspectConfigCmd(String configId) {
        return interceptAnswer(getDelegate().inspectConfigCmd(configId));
    }

    @Override
    public CreateConfigCmd createConfigCmd() {
        return interceptAnswer(getDelegate().createConfigCmd());
    }

    @Override
    public ListConfigsCmd listConfigsCmd() {
        return interceptAnswer(getDelegate().listConfigsCmd());
    }

    @Override
    public RemoveSecretCmd removeSecretCmd(String secretId) {
        return interceptAnswer(getDelegate().removeSecretCmd(secretId));
    }

    @Override
    public CreateSecretCmd createSecretCmd(SecretSpec secretSpec) {
        return interceptAnswer(getDelegate().createSecretCmd(secretSpec));
    }

    @Override
    public ListSecretsCmd listSecretsCmd() {
        return interceptAnswer(getDelegate().listSecretsCmd());
    }

    @Override
    public RemoveSwarmNodeCmd removeSwarmNodeCmd(String swarmNodeId) {
        return interceptAnswer(getDelegate().removeSwarmNodeCmd(swarmNodeId));
    }

    @Override
    public ResizeContainerCmd resizeContainerCmd(String containerId) {
        return interceptAnswer(getDelegate().resizeContainerCmd(containerId));
    }

    @Override
    public SaveImagesCmd saveImagesCmd() {
        return interceptAnswer(getDelegate().saveImagesCmd());
    }

    @Override
    public ResizeExecCmd resizeExecCmd(String execId) {
        return interceptAnswer(getDelegate().resizeExecCmd(execId));
    }
}
