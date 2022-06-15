package datenbankpaket;

public class PersistentQueries {
	private String sqlInsert = "INSERT INTO Cards(question, answer) VALUES (?,?)";
			  private String sqlSelectID = "SELECT ID FROM Cards";
			  private String sqlSelect = "SELECT question and answer FROM Cards";


			  public String getSqlInsert() {
			   return sqlInsert;
			  }

			  public void setSqlInsert(String sqlInsert) {
			    this.sqlInsert = sqlInsert;
			  }


			  public String getSqlSelectID() {
			   return sqlSelectID;
			   }

			  public void setSqlSelectID(String sqlSelectID) {
			   this.sqlSelectID = sqlSelectID;
			  }

			   public String getSqlSelect() {
			   return sqlSelect;
			  }

			  public void setSqlSelect(String sqlSelect) {
			   this.sqlSelect = sqlSelect;
			  }
}
