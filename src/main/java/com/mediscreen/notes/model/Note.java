package com.mediscreen.notes.model;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "notes")
public class Note {

    @Id
    private String id;

    private LocalDate date;

    @NotBlank(message = "le patient id est obligatoire ")
    private String patientId;

    @NotBlank(message =" le contenu est obligatoire")
    private String content;
}
