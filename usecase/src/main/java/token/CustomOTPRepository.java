package token;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomOTPRepository extends TokenRepository<CustomOTP> {
    @Override
    void add(CustomOTP customOTP);
    @Override
    void update(CustomOTP customOTP);
    @Override
    Optional<CustomOTP> findByRequestFrom(String requestFrom);
    @Override
    CustomOTP get(UUID id);
    @Override
    List<CustomOTP> tokens();
}
