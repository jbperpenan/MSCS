package org.jbp.csc611m.mc01.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "CREW")
public class Crew {

    @Id
    @GeneratedValue
    private Long id;

    private String director;
    private String cast;
    private Integer movieId;

    public Crew(String director, String cast, Integer movieId) {
        this.director = director;
        this.cast = cast;
        this.movieId = movieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Crew crew1 = (Crew) o;

        if (id != null ? !id.equals(crew1.id) : crew1.id != null) return false;
        if (director != null ? !director.equals(crew1.director) : crew1.director != null) return false;
        return cast != null ? cast.equals(crew1.cast) : crew1.cast == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (director != null ? director.hashCode() : 0);
        result = 31 * result + (cast != null ? cast.hashCode() : 0);
        return result;
    }
}
