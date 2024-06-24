package middle.example.gpb.gateways.user_gateway;

import middle.example.gpb.models.CreateUserRequestV2;

public interface UserGateway {

    public boolean newUserRegisterResponse(CreateUserRequestV2 userRequest);
}
