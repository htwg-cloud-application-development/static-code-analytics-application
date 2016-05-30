package de.htwg.konstanz.cloud.model;

import java.util.Collection;
import java.util.LinkedHashSet;

import lombok.Data;

@Data
public class Assignment {

	private Collection<File> files = new LinkedHashSet<File>();
}
