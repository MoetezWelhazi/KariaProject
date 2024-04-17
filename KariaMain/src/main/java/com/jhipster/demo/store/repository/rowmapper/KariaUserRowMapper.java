package com.jhipster.demo.store.repository.rowmapper;

import com.jhipster.demo.store.domain.KariaUser;
import com.jhipster.demo.store.domain.enumeration.Gender;
import com.jhipster.demo.store.domain.enumeration.RoleEnum;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link KariaUser}, with proper type conversions.
 */
@Service
public class KariaUserRowMapper implements BiFunction<Row, String, KariaUser> {

    private final ColumnConverter converter;

    public KariaUserRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link KariaUser} stored in the database.
     */
    @Override
    public KariaUser apply(Row row, String prefix) {
        KariaUser entity = new KariaUser();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setFirstName(converter.fromRow(row, prefix + "_first_name", String.class));
        entity.setLastName(converter.fromRow(row, prefix + "_last_name", String.class));
        entity.setGender(converter.fromRow(row, prefix + "_gender", Gender.class));
        entity.setEmail(converter.fromRow(row, prefix + "_email", String.class));
        entity.setPhone(converter.fromRow(row, prefix + "_phone", String.class));
        entity.setAddressLine1(converter.fromRow(row, prefix + "_address_line_1", String.class));
        entity.setAddressLine2(converter.fromRow(row, prefix + "_address_line_2", String.class));
        entity.setCity(converter.fromRow(row, prefix + "_city", String.class));
        entity.setRole(converter.fromRow(row, prefix + "_role", RoleEnum.class));
        entity.setAvatarContentType(converter.fromRow(row, prefix + "_avatar_content_type", String.class));
        entity.setAvatar(converter.fromRow(row, prefix + "_avatar", byte[].class));
        entity.setUserId(converter.fromRow(row, prefix + "_user_id", Long.class));
        return entity;
    }
}
