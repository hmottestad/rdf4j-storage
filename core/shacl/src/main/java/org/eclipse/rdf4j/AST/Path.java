package org.eclipse.rdf4j.AST;

import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.repository.sail.SailRepositoryConnection;
import org.eclipse.rdf4j.vocabulary.SH;

/**
 * Created by heshanjayasinghe on 6/10/17.
 */
public class Path {
    Resource path;

    public Path(Resource next, SailRepositoryConnection connection) {
        super();

        ValueFactory vf = connection.getValueFactory();
        path = (Resource) connection.getStatements(next, vf.createIRI(SH.BASE_URI, "path"), null, true).next().getObject();

    }

    @Override
    public String toString() {
        return "Path{" +
                "path=" + path +
                '}';
    }
}