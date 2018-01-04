package org.eclipse.rdf4j.sail.shacl.planNodes;

import org.eclipse.rdf4j.common.iteration.CloseableIteration;
import org.eclipse.rdf4j.sail.SailException;
import org.eclipse.rdf4j.sail.shacl.plan.PlanNode;
import org.eclipse.rdf4j.sail.shacl.plan.Tuple;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

public class BufferedTupleFromFilter implements PlanNode, PushBasedPlanNode, SupportsDepthProvider{


	private CloseableIteration<Tuple, SailException> parentIterator;

	LinkedList<Tuple> next = new LinkedList<>();
	private DepthProvider depthProvider;

	@Override
	public CloseableIteration<Tuple, SailException> iterator() {
		return new CloseableIteration<Tuple, SailException>() {

			private void calculateNext() {
				while(next.isEmpty() && parentIterator.hasNext()){
					parentIterator.next();
				}
			}

			@Override
			public void close() throws SailException {
				parentIterator.close();
			}

			@Override
			public boolean hasNext() throws SailException {
				calculateNext();
				return !next.isEmpty();
			}

			@Override
			public Tuple next() throws SailException {
				calculateNext();

				return next.removeLast();
			}



			@Override
			public void remove() throws SailException {

			}
		};
	}

	@Override
	public int depth() {
		return depthProvider.depth()+1;
	}

	@Override
	public void push(Tuple t) {
		next.addFirst(t);
	}

	@Override
	public void parentIterator(CloseableIteration<Tuple, SailException> iterator) {
			parentIterator = iterator;
	}


	@Override
	public void receiveDepthProvider(DepthProvider depthProvider) {
		this.depthProvider = depthProvider;
	}
}