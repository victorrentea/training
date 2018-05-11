package ro.victor.unittest.mocks;

class OldSingleton{
		private static OldSingleton INSTANCE;
		private OldSingleton() {
		}
		public static OldSingleton getInstance() {
			if (INSTANCE == null) {
				INSTANCE = new OldSingleton();
			}
//			return INSTANCE;
			throw new IllegalArgumentException();
		}
		private String state;
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
	}