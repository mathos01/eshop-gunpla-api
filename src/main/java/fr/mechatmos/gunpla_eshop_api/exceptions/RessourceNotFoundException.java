package fr.mechatmos.gunpla_eshop_api.exceptions;

public class RessourceNotFoundException extends RuntimeException {
    public RessourceNotFoundException(String message){
        super(message);
    }
}
