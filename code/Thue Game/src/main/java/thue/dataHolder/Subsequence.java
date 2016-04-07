package thue.dataHolder;

import java.util.List;

public class Subsequence {

	public Subsequence(List<Integer> numbers, int begin, int length) {
		this.numbers = numbers;
		this.begin = begin;
		this.length = length;
	}

	private List<Integer> numbers;
	private int begin;
	private int length;

	public List<Integer> getNumbers() {
		return numbers;
	}

	public void setNumbers(List<Integer> numbers) {
		this.numbers = numbers;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
}
