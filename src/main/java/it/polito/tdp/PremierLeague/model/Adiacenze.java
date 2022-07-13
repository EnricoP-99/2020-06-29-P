package it.polito.tdp.PremierLeague.model;

public class Adiacenze {

	private Match m1;
	private Match m2;
	private int peso;
	public Adiacenze(Match m1, Match m2, int peso) {
		super();
		this.m1 = m1;
		this.m2 = m2;
		this.peso = peso;
	}
	public Match getM1() {
		return m1;
	}
	public void setM1(Match m1) {
		this.m1 = m1;
	}
	public Match getM2() {
		return m2;
	}
	public void setM2(Match m2) {
		this.m2 = m2;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((m1 == null) ? 0 : m1.hashCode());
		result = prime * result + ((m2 == null) ? 0 : m2.hashCode());
		result = prime * result + peso;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adiacenze other = (Adiacenze) obj;
		if (m1 == null) {
			if (other.m1 != null)
				return false;
		} else if (!m1.equals(other.m1))
			return false;
		if (m2 == null) {
			if (other.m2 != null)
				return false;
		} else if (!m2.equals(other.m2))
			return false;
		if (peso != other.peso)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Adiacenze [m1=" + m1 + ", m2=" + m2 + ", peso=" + peso + "]";
	}
	
	
}
