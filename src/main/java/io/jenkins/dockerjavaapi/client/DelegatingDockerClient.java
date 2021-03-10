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
import javax.annotation.Nonnull;

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
 * having to implement all of them.
 * </p>
 * If you are writing a Jenkins plugin that needs a class to implement/wrap
 * {@link DockerClient}, you'd be best advised to extend this one, otherwise
 * your code could fail whenever the version of this plugin changes.
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
    public DelegatingDockerClient(@Nonnull DockerClient delegate) {
        this.delegate = delegate;
    }

    /**
     * Obtains the underlying {@link DockerClient} interface. Subclasses can
     * override this if they need to hook into every call.
     *
     * @return the {@link DockerClient} to be delegated to.
     */
    @Nonnull
    protected DockerClient getDelegate() {
        return delegate;
    }

    @Override
    public AttachContainerCmd attachContainerCmd(String arg0) {
        return getDelegate().attachContainerCmd(arg0);
    }

    @Override
    public AuthCmd authCmd() {
        return getDelegate().authCmd();
    }

    @Override
    public AuthConfig authConfig() throws DockerException {
        return getDelegate().authConfig();
    }

    @Override
    public BuildImageCmd buildImageCmd() {
        return getDelegate().buildImageCmd();
    }

    @Override
    public BuildImageCmd buildImageCmd(File arg0) {
        return getDelegate().buildImageCmd(arg0);
    }

    @Override
    public BuildImageCmd buildImageCmd(InputStream arg0) {
        return getDelegate().buildImageCmd(arg0);
    }

    @Override
    public void close() throws IOException {
        getDelegate().close();
    }

    @Override
    public CommitCmd commitCmd(String arg0) {
        return getDelegate().commitCmd(arg0);
    }

    @Override
    public ConnectToNetworkCmd connectToNetworkCmd() {
        return getDelegate().connectToNetworkCmd();
    }

    @Override
    public ContainerDiffCmd containerDiffCmd(String arg0) {
        return getDelegate().containerDiffCmd(arg0);
    }

    @Override
    public CopyArchiveFromContainerCmd copyArchiveFromContainerCmd(String arg0, String arg1) {
        return getDelegate().copyArchiveFromContainerCmd(arg0, arg1);
    }

    @Override
    public CopyArchiveToContainerCmd copyArchiveToContainerCmd(String arg0) {
        return getDelegate().copyArchiveToContainerCmd(arg0);
    }

    @Override
    public CopyFileFromContainerCmd copyFileFromContainerCmd(String arg0, String arg1) {
        return getDelegate().copyFileFromContainerCmd(arg0, arg1);
    }

    @Override
    public CreateContainerCmd createContainerCmd(String arg0) {
        return getDelegate().createContainerCmd(arg0);
    }

    @Override
    public CreateImageCmd createImageCmd(String arg0, InputStream arg1) {
        return getDelegate().createImageCmd(arg0, arg1);
    }

    @Override
    public CreateNetworkCmd createNetworkCmd() {
        return getDelegate().createNetworkCmd();
    }

    @Override
    public CreateVolumeCmd createVolumeCmd() {
        return getDelegate().createVolumeCmd();
    }

    @Override
    public DisconnectFromNetworkCmd disconnectFromNetworkCmd() {
        return getDelegate().disconnectFromNetworkCmd();
    }

    @Override
    public EventsCmd eventsCmd() {
        return getDelegate().eventsCmd();
    }

    @Override
    public ExecCreateCmd execCreateCmd(String arg0) {
        return getDelegate().execCreateCmd(arg0);
    }

    @Override
    public ExecStartCmd execStartCmd(String arg0) {
        return getDelegate().execStartCmd(arg0);
    }

    @Override
    public InfoCmd infoCmd() {
        return getDelegate().infoCmd();
    }

    @Override
    public InspectContainerCmd inspectContainerCmd(String arg0) {
        return getDelegate().inspectContainerCmd(arg0);
    }

    @Override
    public InspectExecCmd inspectExecCmd(String arg0) {
        return getDelegate().inspectExecCmd(arg0);
    }

    @Override
    public InspectImageCmd inspectImageCmd(String arg0) {
        return getDelegate().inspectImageCmd(arg0);
    }

    @Override
    public InspectNetworkCmd inspectNetworkCmd() {
        return getDelegate().inspectNetworkCmd();
    }

    @Override
    public InspectVolumeCmd inspectVolumeCmd(String arg0) {
        return getDelegate().inspectVolumeCmd(arg0);
    }

    @Override
    public KillContainerCmd killContainerCmd(String arg0) {
        return getDelegate().killContainerCmd(arg0);
    }

    @Override
    public ListContainersCmd listContainersCmd() {
        return getDelegate().listContainersCmd();
    }

    @Override
    public ListImagesCmd listImagesCmd() {
        return getDelegate().listImagesCmd();
    }

    @Override
    public ListNetworksCmd listNetworksCmd() {
        return getDelegate().listNetworksCmd();
    }

    @Override
    public ListVolumesCmd listVolumesCmd() {
        return getDelegate().listVolumesCmd();
    }

    @Override
    public LoadImageCmd loadImageCmd(InputStream arg0) {
        return getDelegate().loadImageCmd(arg0);
    }

    @Override
    public LogContainerCmd logContainerCmd(String arg0) {
        return getDelegate().logContainerCmd(arg0);
    }

    @Override
    public PauseContainerCmd pauseContainerCmd(String arg0) {
        return getDelegate().pauseContainerCmd(arg0);
    }

    @Override
    public PingCmd pingCmd() {
        return getDelegate().pingCmd();
    }

    @Override
    public PullImageCmd pullImageCmd(String arg0) {
        return getDelegate().pullImageCmd(arg0);
    }

    @Override
    public PushImageCmd pushImageCmd(String arg0) {
        return getDelegate().pushImageCmd(arg0);
    }

    @Override
    public PushImageCmd pushImageCmd(Identifier arg0) {
        return getDelegate().pushImageCmd(arg0);
    }

    @Override
    public RemoveContainerCmd removeContainerCmd(String arg0) {
        return getDelegate().removeContainerCmd(arg0);
    }

    @Override
    public RemoveImageCmd removeImageCmd(String arg0) {
        return getDelegate().removeImageCmd(arg0);
    }

    @Override
    public RemoveNetworkCmd removeNetworkCmd(String arg0) {
        return getDelegate().removeNetworkCmd(arg0);
    }

    @Override
    public RemoveVolumeCmd removeVolumeCmd(String arg0) {
        return getDelegate().removeVolumeCmd(arg0);
    }

    @Override
    public RenameContainerCmd renameContainerCmd(String arg0) {
        return getDelegate().renameContainerCmd(arg0);
    }

    @Override
    public RestartContainerCmd restartContainerCmd(String arg0) {
        return getDelegate().restartContainerCmd(arg0);
    }

    @Override
    public SaveImageCmd saveImageCmd(String arg0) {
        return getDelegate().saveImageCmd(arg0);
    }

    @Override
    public SearchImagesCmd searchImagesCmd(String arg0) {
        return getDelegate().searchImagesCmd(arg0);
    }

    @Override
    public StartContainerCmd startContainerCmd(String arg0) {
        return getDelegate().startContainerCmd(arg0);
    }

    @Override
    public StatsCmd statsCmd(String arg0) {
        return getDelegate().statsCmd(arg0);
    }

    @Override
    public StopContainerCmd stopContainerCmd(String arg0) {
        return getDelegate().stopContainerCmd(arg0);
    }

    @Override
    public TagImageCmd tagImageCmd(String arg0, String arg1, String arg2) {
        return getDelegate().tagImageCmd(arg0, arg1, arg2);
    }

    @Override
    public TopContainerCmd topContainerCmd(String arg0) {
        return getDelegate().topContainerCmd(arg0);
    }

    @Override
    public UnpauseContainerCmd unpauseContainerCmd(String arg0) {
        return getDelegate().unpauseContainerCmd(arg0);
    }

    @Override
    public UpdateContainerCmd updateContainerCmd(String arg0) {
        return getDelegate().updateContainerCmd(arg0);
    }

    @Override
    public VersionCmd versionCmd() {
        return getDelegate().versionCmd();
    }

    @Override
    public WaitContainerCmd waitContainerCmd(String arg0) {
        return getDelegate().waitContainerCmd(arg0);
    }

    @Override
    public InitializeSwarmCmd initializeSwarmCmd(SwarmSpec swarmSpec) {
        return getDelegate().initializeSwarmCmd(swarmSpec);
    }

    @Override
    public InspectSwarmCmd inspectSwarmCmd() {
        return getDelegate().inspectSwarmCmd();
    }

    @Override
    public JoinSwarmCmd joinSwarmCmd() {
        return getDelegate().joinSwarmCmd();
    }

    @Override
    public LeaveSwarmCmd leaveSwarmCmd() {
        return getDelegate().leaveSwarmCmd();
    }

    @Override
    public UpdateSwarmCmd updateSwarmCmd(SwarmSpec swarmSpec) {
        return getDelegate().updateSwarmCmd(swarmSpec);
    }

    @Override
    public UpdateSwarmNodeCmd updateSwarmNodeCmd() {
        return getDelegate().updateSwarmNodeCmd();
    }

    @Override
    public ListSwarmNodesCmd listSwarmNodesCmd() {
        return getDelegate().listSwarmNodesCmd();
    }

    @Override
    public ListServicesCmd listServicesCmd() {
        return getDelegate().listServicesCmd();
    }

    @Override
    public CreateServiceCmd createServiceCmd(ServiceSpec serviceSpec) {
        return getDelegate().createServiceCmd(serviceSpec);
    }

    @Override
    public InspectServiceCmd inspectServiceCmd(String serviceId) {
        return getDelegate().inspectServiceCmd(serviceId);
    }

    @Override
    public UpdateServiceCmd updateServiceCmd(String serviceId, ServiceSpec serviceSpec) {
        return getDelegate().updateServiceCmd(serviceId, serviceSpec);
    }

    @Override
    public RemoveServiceCmd removeServiceCmd(String serviceId) {
        return getDelegate().removeServiceCmd(serviceId);
    }

    @Override
    public ListTasksCmd listTasksCmd() {
        return getDelegate().listTasksCmd();
    }

    @Override
    public LogSwarmObjectCmd logServiceCmd(String serviceId) {
        return getDelegate().logServiceCmd(serviceId);
    }

    @Override
    public LogSwarmObjectCmd logTaskCmd(String taskId) {
        return getDelegate().logTaskCmd(taskId);
    }

    @Override
    public PruneCmd pruneCmd(PruneType pruneType) {
        return getDelegate().pruneCmd(pruneType);
    }
}
