package dev.macrobug.readitlater;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Website.class)
public abstract class Website_ {

	public static volatile SingularAttribute<Website, Publisher> publisher;
	public static volatile SingularAttribute<Website, Long> id;
	public static volatile SingularAttribute<Website, String> title;
	public static volatile SingularAttribute<Website, String> url;

}

