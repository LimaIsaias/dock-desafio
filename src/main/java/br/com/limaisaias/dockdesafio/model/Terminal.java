package br.com.limaisaias.dockdesafio.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Terminal implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @EqualsAndHashCode.Include
    @NotNull(message = "Logic required")
    private Integer logic;

    @NotEmpty(message = "Serial required")
    private String serial;

    @NotEmpty(message = "Model required")
    private String model;

    @Min(value = 0, message = "Min is 0")
    private Integer sam;

    private String ptid;

    private Integer plat;

    @NotEmpty(message = "Version required")
    private String version;

    private Integer mxr;

    private Integer mxf;

    private String verfm;

}
