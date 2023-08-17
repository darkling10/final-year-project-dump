package com.example.pdfprocessing.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@Builder
@Getter
@Setter
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "extractedData")
public class PdfDataModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String subName;
    private String description;

    @Lob
    @Column(length = 16777215)
    private byte[] dataFile;

}
