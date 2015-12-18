package batch.services;

import batch.model.domain.ServicePlatform;

public interface PlatformService {

	ServicePlatform getPlatformForPostalCode(String postalCode);

}
