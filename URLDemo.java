
	private String getDate() {
 
		// 从网站抓取数据
		String table = catchData();
		String date = "";
 
		// 使用正则表达式，获取对应的数据
		Pattern places = Pattern.compile("(<p align=\"center\">)([^\\s]*)");
		Matcher matcher = places.matcher(table);
		while (matcher.find()) {
			System.out.println(matcher.group(2));
			date = matcher.group(2);
		}
		return date;
	}

		     Pattern places = Pattern.compile("(?<=\\[Proxy\\])[^\\¥]+?(?=\\[Proxy Group\\])");
		     Matcher matcher = places.matcher(table);
		     
		     for (String retval: str.split("-", 2)){
            System.out.println(retval);
        }