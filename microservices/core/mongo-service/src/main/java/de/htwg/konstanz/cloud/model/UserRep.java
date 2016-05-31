package de.htwg.konstanz.cloud.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class UserRep {

    @Id
    private String id;
    private String repositoryUrl;
    private String numberOfErrors;
    private String numberOfWarnings;
    private String numberOfIgnores;
    private String totalExpendedTime;
    private String timestamp;
    private Object assignments;

  	@Override
	public String toString() {
		return "UserRep [id=" + id + ", repositoryUrl=" + repositoryUrl + ", numberOfErrors=" + numberOfErrors
				+ ", numberOfWarnings=" + numberOfWarnings + ", numberOfIgnores=" + numberOfIgnores
				+ ", totalExpendedTime=" + totalExpendedTime + ", assignments=]";
	}
   
}
