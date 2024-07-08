package middle.example.gpb.gateways.transfer_gateway;

import middle.example.gpb.models.CreateTransferRequest;
import middle.example.gpb.models.TransferResponseV2;

public interface TransferGateway {

    public TransferResponseV2 postTransferResponse(CreateTransferRequest transferRequest);
}
