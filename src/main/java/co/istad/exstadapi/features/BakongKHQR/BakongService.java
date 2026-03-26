package co.istad.exstadapi.features.BakongKHQR;

import co.istad.exstadapi.features.BakongKHQR.dto.BakongDataRequest;
import kh.gov.nbc.bakong_khqr.model.KHQRData;
import kh.gov.nbc.bakong_khqr.model.KHQRResponse;
import org.springframework.http.ResponseEntity;

public interface BakongService {
    KHQRResponse<KHQRData> generateQR(BakongDataRequest request);
    ResponseEntity<byte[]> getQRImage(KHQRData qr);
    ResponseEntity<?> checkTransactionByMD5(String md5);
}
