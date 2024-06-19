package it.unicam.cs.mpmgc.formula1.model.dto;

public interface DTOMapper<T, K> {

     T fromDTO(K dto);

     K toDTO(T dto);

}
