package domain.administrator.spi;

import domain.administrator.Administrator;

import java.util.UUID;

public interface AdministratorRepository {
    Administrator get(UUID administratorId);
}
