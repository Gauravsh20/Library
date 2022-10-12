package com.Infinite.LibraryProject;
import java.sql.Date;
public class TranBook {
	


		private String userName;
		private int bookId;
		private Date fromDate;
		private Date ToDate;
		
		public TranBook(String userName, int bookId, Date fromDate, Date toDate) {
			super();
			this.userName = userName;
			this.bookId = bookId;
			this.fromDate = fromDate;
			this.ToDate = toDate;
		}
		public TranBook() {
			// TODO Auto-generated constructor stub
		}
		public Date getToDate() {
			return ToDate;
		}
		public void setToDate(Date toDate) {
			ToDate = toDate;
		}
		@Override
		public String toString() {
			return "TranBook [userName=" + userName + ", bookId=" + bookId + ", fromDate=" + fromDate + ", ToDate="
					+ ToDate + "]";
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public int getBookId() {
			return bookId;
		}
		public void setBookId(int bookId) {
			this.bookId = bookId;
		}
		public Date getFromDate() {
			return fromDate;
		}
		public void setFromDate(Date fromDate) {
			this.fromDate = fromDate;
		}
		
		
	}