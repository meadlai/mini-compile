package com.meadlai.compile.snake;

import java.util.Objects;

import javafx.scene.paint.Color;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Node {
	private char value;
	private int locationX;
	private int locationY;

	public Node setHeader() {
		this.value = ' ';
		return this;
	}

	public Node(char ch, Color color) {
		this.value = ch;
//		if(color!=null) {
//			this.color = color;
//		}
	}

	public Node() {
	}

	public static Node create() {
		return new Node();
	}

	public Node translate(int dx, int dy) {
		this.locationX = this.locationX + dx;
		this.locationY = this.locationY + dy;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(locationX, locationY, value);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Node)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Node other = (Node) obj;
		return locationX == other.locationX && locationY == other.locationY && value == other.value;
	}

}
