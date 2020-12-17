package by.itechart.retailers.service.interfaces;

public interface CongratulationsService {
    void sendCongratulations();

    void sendSystemAdminNotification(RuntimeException ex);
}
