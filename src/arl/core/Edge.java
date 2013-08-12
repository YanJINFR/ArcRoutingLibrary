package arl.core;

import arl.graph.util.Pair;

/**
 * Edge abstraction.  Provides most general contract for all Edge objects.
 * @author oliverlum
 *
 */
public abstract class Edge {
	
	private String mLabel;
	private int mId;
	private Pair<Vertex> mEndpoints;
	private double mCost;
	
	public Edge(String label, int id, Pair<Vertex> endpoints, double cost)
	{
		setLabel(label);
		setId(id);
		setEndpoints(endpoints);
		setCost(cost);
	}

	//==================================
	// Getters and Setters
	//==================================
	
	public String getLabel() {
		return mLabel;
	}

	public void setLabel(String mLabel) {
		this.mLabel = mLabel;
	}

	public int getId() {
		return mId;
	}

	public void setId(int mId) {
		this.mId = mId;
	}

	public Pair<Vertex> getEndpoints() {
		return mEndpoints;
	}

	public void setEndpoints(Pair<Vertex> mEndpoints) {
		this.mEndpoints = mEndpoints;
	}

	public double getCost() {
		return mCost;
	}

	public void setCost(double mCost) {
		this.mCost = mCost;
	}

}
