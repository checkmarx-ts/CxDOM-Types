
class ForEachStmt {
	
	public int sum(int[] a) {
		int result = 0;
		for (int i : a) {
			result += i;
		}
		return result;
	}
	
	public void sny () {
		
		List<FileItem> list = (List<FileItem>)upload.parseRequest(request);
		
		for (FileItem item : list) {
			
			in = item.getInputStream();
		}
		
	}
	
	
}