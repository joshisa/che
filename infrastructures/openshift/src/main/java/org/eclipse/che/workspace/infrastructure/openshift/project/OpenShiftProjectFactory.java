package org.eclipse.che.workspace.infrastructure.openshift.project;

import static com.google.common.base.MoreObjects.firstNonNull;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import javax.inject.Named;
import org.eclipse.che.api.core.model.workspace.runtime.RuntimeIdentity;
import org.eclipse.che.api.workspace.server.spi.InfrastructureException;
import org.eclipse.che.commons.annotation.Nullable;
import org.eclipse.che.workspace.infrastructure.openshift.OpenShiftClientFactory;

/**
 * Helps to create {@link OpenShiftProject} instances.
 *
 * @author Anton Korneta
 */
@Singleton
public class OpenShiftProjectFactory {

  private final String projectName;
  private final OpenShiftClientFactory clientFactory;

  @Inject
  public OpenShiftProjectFactory(
      @Nullable @Named("che.infra.openshift.project") String projectName,
      OpenShiftClientFactory clientFactory) {
    this.projectName = projectName;
    this.clientFactory = clientFactory;
  }

  public OpenShiftProject create(RuntimeIdentity identity) throws InfrastructureException {
    final String workspaceId = identity.getWorkspaceId();
    final String projectName = firstNonNull(this.projectName, workspaceId);
    return new OpenShiftProject(clientFactory, projectName, workspaceId);
  }
}
