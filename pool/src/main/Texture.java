package main;

public enum Texture {
	SOLID {
		@Override
		public Texture getOpposite() {
			return STRIPED;
		}
	},
	STRIPED {
		@Override
		public Texture getOpposite() {
			return SOLID;
		}
	},
	EIGHT_BALL, CUE_BALL,;

	public Texture getOpposite() {
		return this;
	};
}
