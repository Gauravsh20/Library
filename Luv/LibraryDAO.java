package com.Training.Library;


import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class LibraryDAO {
	
	// Add User
	public String AddUser(String Username,String Password) throws ClassNotFoundException, SQLException {
		Connection connection=ConnectionHelper.getConnection();
		String cmd="Insert into LibUsers (Username,Password) values(?,?)"; 
		PreparedStatement pst=connection.prepareStatement(cmd);
		pst.setString(1, Username);
		pst.setString(2, Password);
		pst.executeUpdate();
		return "Successfully";
		
	}
	
	
	
//Validate User;

	public int authenticate(String user, String password) throws ClassNotFoundException, SQLException{
		Connection connection=ConnectionHelper.getConnection();
		String cmd="select count(*) cnt from libusers where  UserName=? and Password=?";
		PreparedStatement pst=connection.prepareStatement(cmd);
		pst.setString(1, user);
		pst.setString(2, password);
		ResultSet rs=pst.executeQuery();
		rs.next();
		int count=rs.getInt("cnt");
		return count;
	}
	
	
	
	//Search
	
	public List<Books>search(String searchType,String SearchValue) throws ClassNotFoundException, SQLException{
		String sql;
		boolean isValid=true;
		if(searchType.equals("id")){
			sql="SELECT * FROM Books WHERE Id=?";
		}else if(searchType.equals("bookname")){
			sql="SELECT * FROM Books WHERE Name=?";
		}else if(searchType.equals("authorname")){
			sql="SELECT * FROM Books WHERE Author=?";
		}else if(searchType.equals("dept")){
			sql="SELECT * FROM Books WHERE Dept=?";
		}
		else {
			isValid=false;
			sql="Select * from Books";
		}
		Connection connection =ConnectionHelper.getConnection();
		PreparedStatement pst=connection.prepareStatement(sql);
		if(isValid==true){
				pst.setString(1, SearchValue);
			
		}
		ResultSet rs=pst.executeQuery();
		Books books=null;
		List<Books>booksList =new ArrayList<>();
		while(rs.next()){
			books=new Books();
			books.setId(rs.getInt("id"));
			books.setName(rs.getString("name"));
			books.setAuthor(rs.getString("author"));
			books.setEdition(rs.getString("edition"));
			books.setDept(rs.getString("dept"));
			books.setNoOfBooks(rs.getInt("TotalBooks"));
			booksList.add(books);
		}
		return booksList;
	}
	
	//Issues books
	
	public String issueBook(String userName,int bookId) throws ClassNotFoundException, SQLException{
		int count=issueorNot(userName, bookId);
		if(count==0){
			
		Connection connection=ConnectionHelper.getConnection();
		String sql="insert into TranBook(UserName,Bookid) value(?,?)";
		PreparedStatement pst=connection.prepareStatement(sql);
		pst.setString(1,userName );
		pst.setInt(2, bookId);
		pst.executeUpdate();
		sql="Update Books set TotalBooks=TotalBooks-1 where id=?";
		pst=connection.prepareStatement(sql);
		pst.setInt(1, bookId);
		pst.executeUpdate();
		return "Book with Id "+bookId+" Issued Successfully...";
		}else{
			return "Book with Id "+bookId+" Already Issued Successfully...";
		}
		
	}
	public int issueorNot(String userName,int bookId) throws ClassNotFoundException, SQLException{
		Connection connection=ConnectionHelper.getConnection();
		String sql="select count(*)cnt from TranBook where UserName=? and BookId=?";
		PreparedStatement pst=connection.prepareStatement(sql);
		pst.setString(1, userName);
		pst.setInt(2, bookId);
		ResultSet rs=pst.executeQuery();
		rs.next();
		int count=rs.getInt("cnt");
		return count;
				
		
	}
	public List<TranBook> issueBooks(String user) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionHelper.getConnection();
		String sql = "select * from TranBook where UserName=?";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setString(1, user);
		ResultSet rs = pst.executeQuery();
		TranBook tranBook = null;
		List<TranBook> tranBookList = new ArrayList<TranBook>();
		while(rs.next()) {
			tranBook = new TranBook();
			tranBook.setBookId(rs.getInt("BookId"));
			tranBook.setUserName(user);
			tranBook.setFromDate(rs.getDate("FromDate"));
			tranBookList.add(tranBook);
		}
		return tranBookList;
	}
	
	public TranBook searchTranBook(String user, int bookId) throws SQLException, ClassNotFoundException{
		Connection connection = ConnectionHelper.getConnection();
		String sql = "select * from TranBook where UserName=? and BookId=?";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setString(1, user);
		pst.setInt(2, bookId);
		ResultSet rs = pst.executeQuery();
		TranBook tranBook = null;
		if(rs.next()){
			tranBook=new TranBook();
			tranBook.setBookId(rs.getInt("BookId"));
			tranBook.setUserName(user);
			tranBook.setFromDate(rs.getDate("FromDate"));
		}
		return tranBook;
	}
	
	public String returnBooks(String user,int bookId) throws ClassNotFoundException, SQLException{
		Connection connection = ConnectionHelper.getConnection();
		TranBook tranBook=searchTranBook(user, bookId);
		String sql = "Insert into TransReturn (userName ,BookId,FromDate) value(?,?,?)";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setString(1, user);
		pst.setInt(2, bookId);
		pst.setDate(3,tranBook.getFromDate());
		pst.executeUpdate();
		sql="Update Books set TotalBooks=TotalBooks-1 where id=?";
		pst=connection.prepareStatement(sql);
		pst.setInt(1, bookId);
		pst.executeUpdate();
		sql="Delete from tranBook where UserName=? And BookId=?";
		pst = connection.prepareStatement(sql);
		pst.setString(1, user);
		pst.setInt(2, bookId);
		pst.executeUpdate();
		return "Your Book"+bookId+"Returned successfully....";
		
		
	}
	
	public List<TranBook>transHistory(String user ) throws ClassNotFoundException, SQLException{
	Connection connection = ConnectionHelper.getConnection();
	String sql = "SELECT * FROM TransReturn where UserName=?";
	PreparedStatement pst = connection.prepareStatement(sql);
	pst.setString(1, user);
	ResultSet rs = pst.executeQuery();
	TranBook tranBook=null;
	List<TranBook>tranBookList=new ArrayList<TranBook>();
	while(rs.next()){
		tranBook=new TranBook();
		tranBook.setBookId(rs.getInt("BookId"));
		tranBook.setUserName(user);
		tranBook.setFromDate(rs.getDate("FromDate"));
		tranBook.setToDate(rs.getDate("Todate"));
		tranBookList.add(tranBook);
	}
	return tranBookList;
		
	}
	
	//Add Book
	public String AddBook(Books book) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionHelper.getConnection();
		String cmd = "insert into Books(Id,Name,Author,Edition,Dept,TotalBooks)values(?,?,?,?,?,?)";
		PreparedStatement pst = connection.prepareStatement(cmd);
		pst.setInt(1,book.getId());
		pst.setString(2, book.getName());
		pst.setString(3, book.getAuthor());
		pst.setString(4, book.getEdition());
		pst.setString(5, book.getDept());
		pst.setInt(6, book.getNoOfBooks());
		pst.executeUpdate();
		return "Add....";
	}
	
//	public String addEmploy(Employ employ) 
//			throws ClassNotFoundException, SQLException {
//		connection = ConnectionHelper.getConnection();
//		String cmd = "insert into Employ(name,dept,desig,basic) "
//				+ " values(?,?,?,?)";
//		pst = connection.prepareStatement(cmd);
//		pst.setString(1, employ.getName());
//		pst.setString(2, employ.getDept());
//		pst.setString(3, employ.getDesig());
//		pst.setInt(4, employ.getBasic());
//		pst.executeUpdate();
//		return "Record Inserted...";
//	}
//	
}
