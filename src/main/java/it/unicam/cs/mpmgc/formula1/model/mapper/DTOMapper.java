package it.unicam.cs.mpmgc.formula1.model.mapper;

/**
 * It defines the behaviour of a DTO (Data Transfer Object) Mapper.
 * This interface is used to map Json Object (DTO) to Model.
 *
 * @param <T> DTO
 * @param <K> Model
 */
public interface DTOMapper<T, K> {

     K fromDTO(T dto);

     T toDTO(K dto);

}
