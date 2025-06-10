package com.sis.fems.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.StringTokenizer;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Component
@Log4j
public class jspUtil{
	/**
	 * &lt; 를 &amp;lt; 로 변경, 
	 * &gt; 를 &amp;gt; 로 변경, 
	 * 공백을 &amp;nbsp; 로 변경
	 *
	 * @param contents 내용
	 * @return String 변환된 내용
	 */
	public static String toHtmlTag(String contents) {
		int len = contents.length();
		int linenum = 0, i = 0;

		for (i = 0; i < len; i++) {
			if ((contents.charAt(i) == '<') | (contents.charAt(i) == '>') | (contents.charAt(i) == '&') | (contents.charAt(i) == ' '))
				linenum++;
		}

		StringBuffer result = new StringBuffer(len + linenum * 5);

		for (i = 0; i < len; i++) {
			if (contents.charAt(i) == '<') {
				result.append("&lt;");
			} else if (contents.charAt(i) == '>') {
				result.append("&gt;");
			} else if (contents.charAt(i) == '&') {
				result.append("&amp;");
			} else if (contents.charAt(i) == ' ') {
				if (i == 0) {
					result.append("&nbsp;");
				} else {
					if (contents.substring(i - 1, i).equals(" ") | contents.substring(i - 1, i).equals("\n")) {
						if (contents.substring(i + 1, i + 2).equals(" ")) {
							result.append("&nbsp;");
						} else {
							if (contents.substring(i - 2, i - 1).equals(" ")) {
								result.append(" ");
							} else {
								result.append("&nbsp;");
							}
						}
					} else {
						result.append(" ");
					}
				}
			} else {
				result.append(contents.charAt(i));
			}
		}
		return result.toString();
	}


	public static String htmlTagRejectEnter(String dateString) {
		if (dateString==null) return "";

		int len = dateString.length();
		StringBuffer sb = new StringBuffer();

		for(int i=0;i<len;i++) {
			char ch = dateString.charAt(i);
			if(ch=='<') {
				if(len-i <4) {
					sb.append(ch);
					continue;
				}
				String tag=dateString.substring(i,i+4);
				if(tag.equals("<br>")) {
					sb.append("\r\n");
					i +=3;
					continue;
				}
			}
			sb.append(ch);
		}
		return sb.toString();
	}

	public static String toEnterBR(String contents) {
		int len = contents.length();
		int linenum = 0, i = 0;

		for(i = 0; i < len; i++) {
			if(contents.charAt(i) == '\n')
				linenum++;
		}

		StringBuffer result = new StringBuffer(len + linenum * 3);

		for(i = 0; i < len; i++) {
			if (contents.charAt(i) == '\n'){
				result.append("<br>");
				i++;
			} else {
				result.append(contents.charAt(i));
			}
		}

		return result.toString();
	}

	/**
	 * 엔터값을 &lt;BR&gt; 로 바꿔준다.
	 * @param contents 내용
	 * @return String 변환된 내용
	 */
	public static String toBR(String contents) {
		int len = contents.length();
		int linenum = 0, i = 0;

		for (i = 0; i < len; i++) {
			if (contents.charAt(i) == '\n')
				linenum++;
		}

		StringBuffer result = new StringBuffer(len + linenum * 3);

		for (i = 0; i < len; i++) {
			if (contents.charAt(i) == '\n')
				result.append("<br>");
			else if (contents.charAt(i) == ' ') 
				result.append("&nbsp;");
			else
				result.append(contents.charAt(i));
		}

		return result.toString();
	}

	/**
	 * <br> 제거
	 * @param contents 내용
	 * @return String 변환된 내용
	 */
	public static String htmlTagReject(String dateString) {
		if (dateString==null) return "";

		int len = dateString.length();
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<len;i++) {
			char ch = dateString.charAt(i);
			if(ch=='<') {
				if(len-i <4){   
					sb.append(ch);
					continue;
				}
				String tag=dateString.substring(i,i+4); 
				if(tag.equals("<br>")){
					i +=3;
					continue;  
				}
			}
			sb.append(ch);
		}
		return sb.toString();
	}

	/**
	 * 게시판의 답변글 작성시 원문인용시에 사용되는 메소드로 각줄의 맨 앞에 &gt;를 붙여 준다.
	 * @param contents 내용
	 * @return String 변환된 내용
	 */
	public static String reply(String contents) {
		int len = contents.length();
		int linenum = 0, i = 0;

		for (i = 0; i < len; i++) {
			if (contents.charAt(i) == '\n')
				linenum++;
		}

		StringBuffer result = new StringBuffer(len + linenum + 1);

		result.append("> ");

		for (i = 0; i < len; i++) {
			if (contents.charAt(i) == '\n')
				result.append("> ");
			else
				result.append(contents.charAt(i));
		}

		return result.toString();
	}

	/**
	 * 문자열의 특정 패턴을 원하는 패턴으로 바꾼다.
	 * @param str 원본 String
	 * @param pattern 원본 패턴
	 * @param replace 원본 패턴에 대치될 패턴
	 * @return String 변환된 내용 
	 */
	public static String replace(String str, String pattern, String replace) {
		int s = 0;
		int e = 0;
		StringBuffer result = new StringBuffer();

		while ((e = str.indexOf(pattern, s)) >= 0) {
			result.append(str.substring(s, e));
			result.append(replace);
			s = e+pattern.length();
		}
		result.append(str.substring(s));
		return result.toString();
	}

	/**
	 * SimpleDateFormat의 format으로 dateString을 파싱해서 Date를 반환한다.
	 * format은 SimpleDateFormat에서 정의한 바를 따른다.
	 * @param dateString String 타입의 날짜
	 * @param format SimpleDateFormat에서 정의된 format을 사용한 포맷
	 * @return format으로 파싱된 date	
	 */
	public static String formatDate(String dateString) {
		if (dateString==null) return "";

		int len = dateString.length();
		String result = "";
		if(len > 0 && len > 9) { 
			result = dateString.substring(0,4) + "-" + dateString.substring(4,6)+"-"+ dateString.substring(6,8)+" ";
			result += dateString.substring(8,10) + ":" + dateString.substring(10,12)+":"+ dateString.substring(12,14);
		}
		if(len > 0 && len < 9){ 
			result = dateString.substring(0,4) + "-" + dateString.substring(4,6)+"-"+ dateString.substring(6,8)+" ";
		}

		return result;
	}

	/**
	 * formatDate 정의된 패턴을 String으로 바꾼다.  
	 * @param str 원본 String
	 * @param pattern 원본 패턴   
	 * @return String 변환된 내용 
	 */
	public static String formatString(String dateString) {
		if (dateString==null) return "";

		int len = dateString.length();
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<len;i++) {
			char ch = dateString.charAt(i);
			if(ch=='-'||ch==' '||ch==':')continue;
			sb.append(ch);
		}
		if(sb.length()<14) {
			for(int i=sb.length();i<14;i++) {
				if (i==9) {
					sb.append("8");
				} else {
					sb.append('0');
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 8859_1 에서 UTF-8로 인코딩 한다.
	 * @param str 원본 String
	 * @return String 변환된 내용 
	 */
	public static String toUTF8(String str){
		String rstr=null;
		try {
			rstr=(str==null)?"":new String(str.getBytes("8859_1"),"utf-8");
		} catch(UnsupportedEncodingException e) {
		}
		return rstr;
	}

	/**
	 * UTF-8 에서 8859_1 로 인코딩 한다.
	 * @param str 원본 String
	 * @return String 변환된 내용 
	 */
	public static String toUS(String str) {
		String rstr=null;
		try {
			rstr=(str==null)?"":new String(str.getBytes("utf-8"),"8859_1");
		} catch(UnsupportedEncodingException e) {
		}
		return rstr;
	}

	/**
	 * 특정 URL 의 HTML 내용을 String 으로 받아 온다. <BR>
	 * 예) JSP 에서 아래와 같이 사용한다. 
	 * <p>
	 * <pre>
	 * &lt;%<BR>
	 *  String html = getUrl("http://posco.com");<BR>
	 *  out.print(html);<BR>
	 * %&gt;
	 * </pre>
	 * <p>
	 * 주의) html 내에서 URI 로 링크 혹은 Frame 이 짜여져 있다면 제대로 보이지 않을수 있다.
	 * 해당 URL로 접근해 브라우져의 소스보기에서 나타나는 내용을 가져오는것 뿐이다.
	 *
	 * @param url HTML을 가져올 URL
	 * @return String 가져온 HTML
	 */
	public static String getUrl(String url) throws Exception {
		int len;
		InputStream input = (new URL(url)).openStream();
		byte b[] = new byte[64000];
		StringBuffer sb = new StringBuffer();
		while ((len = input.read(b, 0, b.length)) > 0) {
			sb.append(new String(b, 0, len));
		}
		input.close();
		return sb.toString();
	}

	/**
	 * 지정한 길이 보다 길경우 지정한 길이에서 자른후 "..."을 붙여 준다.
	 * 그보다 길지 않을때는 그냥 돌려준다. char 단위로 계산 (한글도 1자)
	 * @param str 원본 String
	 * @param amount String 의 최대 길이 (이보다 길면 이 길이에서 자른다)
	 * @return String 변경된 내용
	 */
	public static String crop(String str, int amount) {
		if (str==null) return "";
		String result = str;
		if(result.length()>amount) result=result.substring(0,amount)+"...";
		return result;
	}

	/**
	 * 지정한 길이 보다 길경우 지정한 길이에서 자른후 맨뒷부분에 지정한 문자열을 붙여 준다.
	 * 그보다 길지 않을때는 그냥 돌려준다. char 단위로 계산 (한글도 1자)
	 * @param str 원본 String
	 * @param amount String 의 최대 길이 (이보다 길면 이 길이에서 자른다)
	 * @param trail amount 보다 str 이 길경우 amount 만큼만 자른다음 trail 을 붙여 준다.
	 * @return String 변경된 내용
	 */
	public static String crop(String str, int amount, String trail) {
		if (str==null) return "";
		String result = str;
		if(result.length()>amount) result=result.substring(0,amount)+trail;
		return result;
	}

	/**
	 * 지정한 길이 보다 길경우 지정한 길이에서 자른후 맨뒷부분에 지정한 문자열을 붙여 준다.
	 * 그보다 길지 않을때는 그냥 돌려준다. Byte 단위로 계산 (한글 = 2자)
	 * <p>
	 * @param str 원본 String
	 * @param amount String 의 최대 길이 (이보다 길면 이 길이에서 자른다)
	 * @param trail amount 보다 str 이 길경우 amount 만큼만 자른다음 trail 을 붙여 준다.
	 * @return String 변경된 내용
	 */
	public static String cropByte(String str, int amount, String trail) throws UnsupportedEncodingException {
		if (str==null) return "";
		String tmp = str;
		int slen = 0, blen = 0;
		char c;
		if(tmp.getBytes("utf-8").length>amount) {
			while (blen+1 < amount) {
				c = tmp.charAt(slen);
				blen++;
				slen++;
				if ( c  > 127 ) blen++;  //2-byte character..
			}
			tmp=tmp.substring(0,slen)+trail;
		}
		return tmp;
	}

	/**
	 * 지정한 길이 보다 작을 때 지정한 길이 만큼 지정한 문자열을 붙여준다. 
	 * @param str 원본 String
	 * @param amount String 의 최대 길이 (이보다 작으면 이 길이 만큼 지정한 문자를 붙여준다.)
	 * @param 붙여줄 문자.
	 * @return String 변경된 내용
	 */
	public static String strMatch(String str, int amount, String trail) throws UnsupportedEncodingException {
		if (str==null) return " ";
		String tmp = "" ;
		if(str.length() < amount) {
			for(int i = 0 ; i < amount - str.length() ; i++) {
				tmp += trail;
			}
			str += tmp;
		}
		return str;
	}

	/**
	 * 문자가 한자리 (숫자)일경우 앞에 0을 붙여준다.
	 * 그보다 길지 않을때는 그냥 돌려준다. char 단위로 계산 (한글도 1자)
	 * @param str 원본 String
	 * @param amount String 의 최소 길이 (이보다 작으면 앞에 "0"을 붙인당)
	 * @return String 변경된 내용
	 */
	public static String addStrZero(String str, int amount) {
		if (str==null) return "";
		String result = str;
		if(result.length() < amount) { 
			int tmpcnt =   amount -result.length() ;
			for(int i=0 ; i<tmpcnt ; i++) {
				result="0"+result; 
			}
		}
		return result;
	}

	/**
	 * 주어진 문자열에 URL(www)이 포함되어 있을 경우 이를 Link 걸어준다.
	 * <p>
	 * @param str 원본 String
	 * @return String 변경된 내용
	 */
	public static String wwwLink(String str) {
		if (str==null) return "";

		String tmp = str;
		int itmp = 0;
		int wend = 0;

		StringBuffer sb = new StringBuffer();
		sb.append("");

		while(tmp.indexOf("http://")>-1) {
			itmp = tmp.indexOf("http://");
			wend = tmp.indexOf(" ",itmp);
			if (wend>tmp.indexOf(")",itmp) && tmp.indexOf(")",itmp)>-1) wend = tmp.indexOf(")",itmp);
			if (wend>tmp.indexOf("<",itmp) && tmp.indexOf("<",itmp)>-1) wend = tmp.indexOf("<",itmp);
			if (wend==-1) wend = tmp.length();
			sb.append(tmp.substring(0,itmp));

			if(itmp>3 && tmp.substring(itmp-3,itmp).indexOf("=")>-1) {
				wend = tmp.indexOf("</a>",itmp)+3;

				if (wend==2) wend = tmp.indexOf(">",itmp);
					sb.append(tmp.substring(itmp,wend));
			} else {
				sb.append("<a href=\""+tmp.substring(itmp,wend)+"\" target=\"_blank\" >");
				sb.append(tmp.substring(itmp,wend));
				sb.append("</a>");
			}

			tmp=tmp.substring(wend);
		}
		sb.append(tmp);

		tmp = sb.toString();
		sb.setLength(0);

		while(tmp.indexOf("www.")>-1) {
			itmp = tmp.indexOf("www.");
			wend = tmp.indexOf(" ",itmp);
			if (wend>tmp.indexOf(")",itmp) && tmp.indexOf(")",itmp)>-1) wend = tmp.indexOf(")",itmp);
			if (wend>tmp.indexOf("<",itmp) && tmp.indexOf("<",itmp)>-1) wend = tmp.indexOf("<",itmp);
			if (wend==-1) wend = tmp.length();
			sb.append(tmp.substring(0,itmp));
			if(itmp>10 && tmp.substring(itmp-10,itmp).indexOf("=")>-1) {
				wend = tmp.indexOf("</a>",itmp)+3;
				if (wend==2) wend = tmp.indexOf(">",itmp);
					sb.append(tmp.substring(itmp,wend));
			} else {
				sb.append("<a href=\"http://"+tmp.substring(itmp,wend)+"\" target=\"_blank\" >");
				sb.append(tmp.substring(itmp,wend));
				sb.append("</a>");
			}
			tmp=tmp.substring(wend);
		}
		sb.append(tmp);

		return sb.toString();

	}

	/**
	 * String 이 null 또는 문자("") 체크 하여 null 이면 공백문자(" ")를 넘겨준다
	 *
	 * @param str 체크할 String
	 * @return String
	 */
	public static String addStrBlank(String str) {
		String strTmp;
		if (str==null||str.equals(""))
			strTmp = " ";
		else
			strTmp = str;
		return strTmp;
	}

	/**
	 * String 이 null  또는 특수문자(\n,\r ,#) 체크 하여 공백문자를 넘겨준다
	 *
	 * @param str 체크할 String
	 * @return String
	 */
	public static String charCheck(String str) {
		if (str.equals(null)||str.equals("")) return " ";
		String tmp = str;
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<tmp.length();i++) {
			char ch = tmp.charAt(i);
			if(ch=='\n'||ch=='\r'||ch=='#') ch = ' ';
			sb.append(ch);
		}
		return sb.toString();
	}

	public static String charCheck2(String str) {
		if (str.equals(null)||str.equals(" ")) return "";
		String tmp = str;
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<tmp.length();i++){
			char ch = tmp.charAt(i);
			if(ch=='\n'||ch=='\r'||ch=='#'||ch==' ') ch = ' ';
			sb.append(ch);
		}
		return sb.toString();
	}

	/**
	 * String 이 null 인지 체크 하여 null 이면 공백문자("")를 넘겨준다
	 *
	 * @param str 체크할 String
	 * @return String
	 */
	public static String nullCheck(String str)
	{
		String strTmp;
		if (str==null||str.equals(""))
			strTmp = "";
		else
			strTmp = str;
		return strTmp;
	}

	/**
	 * String 이 null 인지 체크 하여 null 이면 지정한값(replace)를 넘겨준다.
	 *
	 * @param str 체크할 String
	 * @param replace null 일 경우 대체할 문자열
	 * @return String
	 */
	public static String nullCheck(String str,String replace)
	{
		String strTmp;
		if (str == null || str.equals(""))
			strTmp = replace;
		else
			strTmp = str;
		return strTmp;
	}

	/**
	 * String 유형의 Parameter 을 받아 올때 사용한다.<BR>
	 * @param req HttpServletRequest
	 * @param id Parameter의 name ( request.getParameter("id") )
	 * @param default_val id의 값(value)가 null 일 경우 default_val 을 return 해준다.
	 * @return id 의 값을 가져오거나 null 일 경우 default_val을 return 한다.
	 */
	public static String getString(javax.servlet.http.HttpServletRequest req, String  id, String default_val) {
		if (req.getParameter(id) != null)
			return jspUtil.toUTF8(req.getParameter(id));
		return jspUtil.toUTF8(default_val);
	}

	/**
	 * int 유형의 Parameter 을 받아 올때 사용한다.<BR>
	 * @param req HttpServletRequest
	 * @param id Parameter의 name ( request.getParameter("id") )
	 * @param default_val id의 값(value)가 null 일 경우 혹은 int형이 아닐경우  default_val 을 return 해준다.
	 * @return id 의 값을 가져오거나 default_val을 return 한다.
	 */

	public static int getInt(javax.servlet.http.HttpServletRequest rq, String  id, int default_val) {
		try {
			return Integer.parseInt(rq.getParameter(id));
		} catch (Exception e) {
			return default_val;
		}
	}

	/**
	 * 시스템 콘솔에서 실행된 명령의 결과를 String 으로 받아 온다
	 * @param cmd 실행한 콘솔 명령
	 * @return 실행 결과
	 */
	public static String getCmd(String cmd) throws IOException {
		Process p = Runtime.getRuntime().exec(cmd);
		InputStream in = p.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String s = "";
		String temp = "";
		while ( (temp = br.readLine()) != null) {
			s += temp + "\n";
		}
		System.out.println(s);
		br.close();
		in.close();
		return s;
	}

	/**
	 * 텍스트 파일을 생성한다.
	 * @param file 파일이 저장될 경로. 시스템의 절대 경로를 넣어준다. (ex. "/home/hsboy/text.txt")
	 * @param contents 저장될 파일의 내용
	 */
	public static void writeFile(String file, String contents) throws IOException {
		FileWriter filew = new FileWriter(new File(file));
		filew.write(contents,0,contents.length());
		filew.close();
	}

	/**
	 * 어제 날짜를 가져온다. (ex, 20020913122001)
	 * @return 어제날자 
	 */
	public static String getYesterday() {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DATE, -1);
		String  sDate = null;  /* 출력 날짜 */ 

		try {
			DecimalFormat df = new DecimalFormat("00"); 
			sDate = calendar.get(Calendar.YEAR)
				  + df.format(calendar.get(Calendar.MONTH)+1) 
				  + df.format(calendar.get(Calendar.DATE));
			return sDate;
		} catch(Exception e) {
			return sDate;
		}
	}

	/**
	 * 오늘 날자를 가져온다. (ex, 20020912122001)
	 * @return 오늘날자
	 */
	public static String getToday(){
		GregorianCalendar calendar = new GregorianCalendar();
		String  sDate = null;  /* 출력 날짜 */

		try {
			DecimalFormat df = new DecimalFormat("00"); 
			sDate = calendar.get(Calendar.YEAR)
				  + df.format(calendar.get(Calendar.MONTH)+1) 
				  + df.format(calendar.get(Calendar.DATE));
			return sDate;
		} catch(Exception e) {
			return sDate;
		}
	}

	/**
	 * 내일 날짜를 가져온다. (ex, 20020913122001)
	 * @return 내일날자 
	 */
	public static String getTomorrow() {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DATE, +1);
		String  sDate = null;  /* 출력 날짜 */ 

		try {
			DecimalFormat df = new DecimalFormat("00"); 
			sDate = calendar.get(Calendar.YEAR)
				  + df.format(calendar.get(Calendar.MONTH)+1) 
				  + df.format(calendar.get(Calendar.DATE));
			return sDate;
		} catch(Exception e) {
			return sDate;
		}
	}

	//두 날짜의 차이 구하기
	public static long getDatedif(String bfday, String btday) throws ParseException {
		long diffSec = 0;
		long diffDays = 0;

		if (( bfday != null && bfday.length() >= 8) && ( btday != null && btday.length() >= 8)) {
			Calendar bfDate = Calendar.getInstance();
			Calendar btDate = Calendar.getInstance();

			Date fdate = new SimpleDateFormat("yyyy-MM-dd").parse(bfday);
			Date tdate = new SimpleDateFormat("yyyy-MM-dd").parse(btday);
			
			bfDate.setTime(fdate); //시작 일자
			btDate.setTime(tdate); //종료 일자

			diffSec = (bfDate.getTimeInMillis() - btDate.getTimeInMillis()) / 1000;
			diffDays = diffSec / (24*60*60); //일자수 차이
		} else {
			diffDays = 0;
		}

		return diffDays;
	}

	/**
	 * 입력받은 날로부터 하루더한 날짜를 가져온다. 
	 * @return 어제날짜 
	 */
	public static String getDate1up(String s) {
		Calendar cal = Calendar.getInstance(); 
		String  sDate = null;  /* 출력 날짜 */ 

		int yyear;

		if (s != null && s.length() >= 8) {
			cal.set(Integer.parseInt(s.substring(0, 4)),
					Integer.parseInt(s.substring(4, 6)),
					Integer.parseInt(s.substring(6, 8)) + 1);
			}
			DecimalFormat df = new DecimalFormat("00");

			yyear = Integer.parseInt(s.substring(0, 4));	

			if(Integer.parseInt(s.substring(6, 8)) == 1) { //달의 1일이면
				if(Integer.parseInt(s.substring(4, 6))== 1) { //1월달이면
				  
				  sDate = cal.get(Calendar.YEAR)-1
					  + df.format(12) 
					  + df.format(31);

			} else if(Integer.parseInt(s.substring(4, 6))== 2) { //3월달이면
				if ((yyear % 400 == 0) || ((yyear % 100 != 0) && (yyear % 4 == 0))) { //윤년이면
					sDate = cal.get(Calendar.YEAR)
						  + df.format(cal.get(Calendar.MONTH)) 
						  + df.format(29);
				} else {
					sDate = cal.get(Calendar.YEAR)
						  + df.format(cal.get(Calendar.MONTH)) 
						  + df.format(28);
				}
			} else if(Integer.parseInt(s.substring(4, 6))== 4
					||Integer.parseInt(s.substring(4, 6))== 6
					||Integer.parseInt(s.substring(4, 6))== 9
					||Integer.parseInt(s.substring(4, 6))== 11) {
				sDate = cal.get(Calendar.YEAR)
					  + df.format(cal.get(Calendar.MONTH)) 
					  + df.format(30);
			} else if(Integer.parseInt(s.substring(4, 6))== 3
					||Integer.parseInt(s.substring(4, 6))== 5
					||Integer.parseInt(s.substring(4, 6))== 7
					||Integer.parseInt(s.substring(4, 6))== 8
					||Integer.parseInt(s.substring(4, 6))== 10
					||Integer.parseInt(s.substring(4, 6))== 12) {
				sDate = cal.get(Calendar.YEAR)
					  + df.format(cal.get(Calendar.MONTH)) 
					  + df.format(31);
			}
		} else {
			sDate = cal.get(Calendar.YEAR)
				  + df.format(cal.get(Calendar.MONTH)) 
				  + df.format(cal.get(Calendar.DATE));
		}

		return sDate;
	}

	/**
	 * 입력받은 날로부터 어제 날짜를 가져온다. (ex, 20020913122001)
	 * @return 어제날짜 
	 */
	public static String getYesterday(int dt, String toDate) {
		GregorianCalendar calendar = new GregorianCalendar();
		
		if(dt == 1)
			calendar.add(Calendar.DATE, -1);
		else if(dt == 2)
			calendar.add(Calendar.DATE, -2);
		String  sDate = null;  /* 출력 날짜 */

		try {
			DecimalFormat df = new DecimalFormat("00");
			sDate = calendar.get(Calendar.YEAR)
				  + df.format(calendar.get(Calendar.MONTH)+1)
				  + df.format(calendar.get(Calendar.DATE));
			return sDate;
		} catch(Exception e) {
			return sDate;
		}
	}

	public static String getDate(int dt, String s) {
		Calendar cal = Calendar.getInstance(); 

		int yyear;

		if (s != null && s.length() >= 8) { 
			if(dt == 1){
				cal.set(Integer.parseInt(s.substring(0, 4)),
						Integer.parseInt(s.substring(4, 6)),
						Integer.parseInt(s.substring(6, 8)) - 1);
			} else if(dt == 2) {
				cal.set(Integer.parseInt(s.substring(0, 4)),
						Integer.parseInt(s.substring(4, 6)),
						Integer.parseInt(s.substring(6, 8)) - 2);
			}
		}

		String  sDate = null;  /* 출력 날짜 */ 

		DecimalFormat df = new DecimalFormat("00");

		yyear = Integer.parseInt(s.substring(0, 4));

		if(Integer.parseInt(s.substring(6, 8))== 1) { //달의 1일이면
			if(Integer.parseInt(s.substring(4, 6))== 1) { //1월달이면
				sDate = cal.get(Calendar.YEAR)-1
					  + df.format(12) 
					  + df.format(31);
			} else if(Integer.parseInt(s.substring(4, 6))== 3) {
				if ((yyear % 400 == 0) || ((yyear % 100 != 0) && (yyear % 4 == 0))) {
					sDate = cal.get(Calendar.YEAR)
						  + df.format(cal.get(Calendar.MONTH)) 
						  + df.format(29);
				} else {
					sDate = cal.get(Calendar.YEAR)
						  + df.format(cal.get(Calendar.MONTH)) 
						  + df.format(28);
				}
			} else if(Integer.parseInt(s.substring(4, 6))== 5
					||Integer.parseInt(s.substring(4, 6))== 7
					||Integer.parseInt(s.substring(4, 6))== 10
					||Integer.parseInt(s.substring(4, 6))== 12) {
				sDate = cal.get(Calendar.YEAR)
					  + df.format(cal.get(Calendar.MONTH)) 
					  + df.format(30);
			} else if(Integer.parseInt(s.substring(4, 6))== 2
					||Integer.parseInt(s.substring(4, 6))== 4
					||Integer.parseInt(s.substring(4, 6))== 6
					||Integer.parseInt(s.substring(4, 6))== 8
					||Integer.parseInt(s.substring(4, 6))== 9
					||Integer.parseInt(s.substring(4, 6))== 11)
			{
				sDate = cal.get(Calendar.YEAR)
					  + df.format(cal.get(Calendar.MONTH)) 
					  + df.format(31);
			}
		} else {
			sDate = cal.get(Calendar.YEAR)
				  + df.format(cal.get(Calendar.MONTH)) 
				  + df.format(cal.get(Calendar.DATE));
		}

		return sDate;
	}

	public static String getMinusDay(String dateFormat, int minusDay) {
		String pattern = "yyyyMMddHHmmss";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		try {
			Date date = formatter.parse(dateFormat);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, minusDay);

			String dateStr = formatter.format(calendar.getTime());

			//System.out.println(dateStr);
			return dateStr;
		} catch(Exception e) {
			return dateFormat;
		}
	}

	public static String getDayType(String dateFormat) {
		String pattern = "yyyyMMddHHmmss";
		String pattern1 = "yyyyMMdd";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		try {
			Date date = formatter.parse(dateFormat);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			//calendar.add(Calendar.DATE, minusDay);

			formatter = new SimpleDateFormat(pattern1);
			String dateStr = formatter.format(calendar.getTime());

			//System.out.println(dateStr);
			return dateStr;
		} catch(Exception e) {
			return dateFormat;
		}
	}

	public static String getDayType(String dateFormat, int minusDay) {
		String pattern = "yyyyMMddHHmmss";
		String pattern1 = "yyyyMMdd";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		try {
			Date date = formatter.parse(dateFormat);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, minusDay);
			formatter = new SimpleDateFormat(pattern1);
			String dateStr = formatter.format(calendar.getTime());
			//System.out.println(dateStr);
			return dateStr;
		} catch(Exception e) {
			return dateFormat;
		}
	}

	public static String getDayType1(String dateFormat, int minusDay) {
		String pattern = "yyyyMMdd";
		String pattern1 = "yyyyMMdd";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		try {
			Date date = formatter.parse(dateFormat);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, minusDay);

			formatter = new SimpleDateFormat(pattern1);
			String dateStr = formatter.format(calendar.getTime());

			//System.out.println(dateStr);
			return dateStr;
		} catch(Exception e) {
			return dateFormat;
		}
	}

	/**
	 * 날짜를 가져온다. (ex, 20020913122001)
	 * @return minusDay 의 값을 더한 날
	 */
	public static String getDayTypeDay(String dateFormat) {
		String pattern = "yyyyMMdd";
		String pattern1 = "dd";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		try {
			Date date = formatter.parse(dateFormat);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			//calendar.add(Calendar.DATE, minusDay);

			formatter = new SimpleDateFormat(pattern1);
			String dateStr = formatter.format(calendar.getTime());

			//System.out.println(dateStr);
			return dateStr;
		} catch(Exception e) {
			return dateFormat;
		}
	}

	/**
	 * 날짜를 가져온다. (ex, 20020913122001)
	 * @return minusDay 의 값을 더한 날
	 */
	public static String getDayTypeMon(String dateFormat) {
		String pattern = "yyyyMMdd";
		String pattern1 = "MM";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		try {
			Date date = formatter.parse(dateFormat);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			//calendar.add(Calendar.DATE, minusDay);

			formatter = new SimpleDateFormat(pattern1);
			String dateStr = formatter.format(calendar.getTime());

			//System.out.println(dateStr);
			return dateStr;
		} catch(Exception e) {
			return dateFormat;
		}
	}

	/**
	 * DateFormat에 따른 오늘 날자를 가져온다. (ex, 2002년 09월 13일)
	 * @param dateFormat 날짜 형식
	 * @return 오늘날자
	 */
	public static String getToday(String dateFormat){
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		String dateStr = null; /* 출력 날짜 */
		dateStr = formatter.format(new Date());
		return dateStr;
	}
	
	public static int getInterMinuTime(String dateFormat ,String dateFormat1) {
		if (dateFormat == null || dateFormat.equals("")) dateFormat = addStrZero(dateFormat,14);
		if (dateFormat1 == null || dateFormat1.equals("")) dateFormat1 = addStrZero(dateFormat1,14);
		Calendar cal = Calendar.getInstance(); 
		Calendar cal_1 = Calendar.getInstance(); 

		cal.set(Integer.parseInt(dateFormat.substring(0, 4)), Integer.parseInt(dateFormat.substring(4, 6)),Integer.parseInt(dateFormat.substring(6, 8)),
				Integer.parseInt(dateFormat.substring(8, 10)),Integer.parseInt(dateFormat.substring(10, 12)),Integer.parseInt(dateFormat.substring(12, 14)));

		cal_1.set(Integer.parseInt(dateFormat1.substring(0, 4)),Integer.parseInt(dateFormat1.substring(4, 6)),Integer.parseInt(dateFormat1.substring(6, 8)),
				Integer.parseInt(dateFormat1.substring(8, 10)),Integer.parseInt(dateFormat1.substring(10, 12)),Integer.parseInt(dateFormat1.substring(12, 14)));
		long interCal = cal.getTimeInMillis() - cal_1.getTimeInMillis();  
		//int minuDiff = (int)(interCal / (60 * 1000)); //분
		int minuDiff = (int)(interCal / (1000)); //초
		return minuDiff;
	}

	public static String getMinusTime(String dateFormat, int minusTime) {
		String pattern = "yyyyMMddHHmmss";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		try {
			Date date = formatter.parse(dateFormat);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MINUTE, minusTime);

			String dateStr = formatter.format(calendar.getTime());

			//System.out.println(dateStr);
			return dateStr;
		} catch(Exception e) {
			return dateFormat;
		}
	}

	public static String getSecTime(String dateFormat, int minusTime) {
		String pattern = "yyyyMMddHHmmss";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		try {
			Date date = formatter.parse(dateFormat);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.SECOND, minusTime);

			String dateStr = formatter.format(calendar.getTime());

			//System.out.println(dateStr);
			return dateStr;
		} catch(Exception e) {
			return dateFormat;
		}
	}

	/**
	 * str 문자열에 포함된 keyword를 단위로 StringBuffer에 삽입한 문자열반환
	 * @param str
	 * @param keyword
	 * @return StringBuffer
	 */
	public static String [] cropString(String str, String keyword) {
		StringTokenizer st = new StringTokenizer(str,keyword);
		String [] subStr = new String[st.countTokens()];  //토큰을 저장할 배열

		//토큰 추출하기
		for(int i=0; i < subStr.length ;i++) {
			subStr[i] = st.nextToken();
		}
		return subStr;
	}


	public static String getWeekJu(String weekday, String thedate){

		int onewday = 0, tday = 0, wday = 0;
		tday = Integer.parseInt(thedate);
		wday = Integer.parseInt(weekday);
		String WhatJu="";

		for(;;) {
			if(tday <= 7) {
				break;
			} else {
				tday = tday - 7;
				continue;
			}
		}

		if(tday == 7) onewday = wday + 1;
		else if(tday == 6) onewday = wday + 2;
		else if(tday == 5) onewday = wday + 3;
		else if(tday == 4) onewday = wday + 4;
		else if(tday == 3) onewday = wday + 5;
		else if(tday == 2) onewday = wday + 6;
		else if(tday == 1) onewday = wday;

		if(onewday > 6) onewday = onewday % 6;

		tday = Integer.parseInt(thedate);

		if(onewday == 0) {
			if(tday <= 7) WhatJu = "1";
			else if(tday > 7 && tday <= 14) WhatJu = "2";
			else if(tday > 14 && tday <= 21) WhatJu = "3";
			else if(tday > 21 && tday <= 28) WhatJu = "4";
			else if(tday > 28 && tday <= 31) WhatJu = "5";
		} else if(onewday == 1) {
			if(tday <= 6) WhatJu = "1";
			else if(tday > 6 && tday <= 13) WhatJu = "2";
			else if(tday > 13 && tday <= 20) WhatJu = "3";
			else if(tday > 20 && tday <= 27) WhatJu = "4";
			else if(tday > 27 && tday <= 31) WhatJu = "5";
		} else if(onewday == 2) {
			if(tday <= 5) WhatJu = "1";
			else if(tday > 5 && tday <= 12) WhatJu = "2";
			else if(tday > 12 && tday <= 19) WhatJu = "3";
			else if(tday > 19 && tday <= 26) WhatJu = "4";
			else if(tday > 26 && tday <= 31) WhatJu = "5";
		} else if(onewday == 3) {
			if(tday <= 4) WhatJu = "1";
			else if(tday > 4 && tday <= 11) WhatJu = "2";
			else if(tday > 11 && tday <= 18) WhatJu = "3";
			else if(tday > 18 && tday <= 25) WhatJu = "4";
			else if(tday > 25 && tday <= 31) WhatJu = "5";
		} else if(onewday == 4) {
			if(tday <= 3) WhatJu = "1";
			else if(tday > 3 && tday <= 10) WhatJu = "2";
			else if(tday > 10 && tday <= 17) WhatJu = "3";
			else if(tday > 17 && tday <= 24) WhatJu = "4";
			else if(tday > 24 && tday <= 31) WhatJu = "5";
		} else if(onewday == 5) {
			if(tday <= 2) WhatJu = "1";
			else if(tday > 2 && tday <= 9) WhatJu = "2";
			else if(tday > 9 && tday <= 16) WhatJu = "3";
			else if(tday > 16 && tday <= 23) WhatJu = "4";
			else if(tday > 23 && tday <= 30) WhatJu = "5";
			else if(tday > 30 && tday <= 31) WhatJu = "6";
		} else if(onewday == 6) {
			if(tday <= 1) WhatJu = "1";
			else if(tday > 1 && tday <= 8) WhatJu = "2";
			else if(tday > 8 && tday <= 15) WhatJu = "3";
			else if(tday > 15 && tday <= 22) WhatJu = "4";
			else if(tday > 22 && tday <= 29) WhatJu = "5";
			else if(tday > 29 && tday <= 31) WhatJu = "6";
		}

		//return String.valueOf(tday);
		return WhatJu;
	}

	/**
	 * 현재날짜/시간 가져온다. (ex, 20020912122001)
	 * @return 오늘날자
	 */
	public static String getTodayConvert() throws Exception {
		GregorianCalendar calendar = new GregorianCalendar();
		String  sDate = null;  /* 출력 날짜 */ 
		try{
			DecimalFormat df = new DecimalFormat("00"); 
			sDate = calendar.get(Calendar.YEAR)
				  + df.format(calendar.get(Calendar.MONTH)+1) 
				  + df.format(calendar.get(Calendar.DATE))
			+ df.format(calendar.get(Calendar.HOUR_OF_DAY))
			+ df.format(calendar.get(Calendar.MINUTE))
			+ df.format(calendar.get(Calendar.SECOND));

			sDate = (mid(sDate, 5, 2)+"-"+mid(sDate, 7, 2)+" "+mid(sDate, 9, 2)+":"+mid(sDate, 11, 2)+":"+mid(sDate, 13, 2));
			return sDate;
		} catch(Exception e) {
			return sDate;
		}
	}

	/**
	 * 현재날짜/시간 가져온다. (ex, 20020912122001)
	 * @return 오늘날자
	 */
	public static String getTodayConvert(int timeType) throws Exception {
		GregorianCalendar calendar = new GregorianCalendar();
		String  sDate = null;  /* 출력 날짜 */ 

		try {
			DecimalFormat df = new DecimalFormat("00");
			sDate = calendar.get(Calendar.YEAR)
				  + df.format(calendar.get(Calendar.MONTH)+1)
				  + df.format(calendar.get(Calendar.DATE))
			+ df.format(calendar.get(Calendar.HOUR_OF_DAY))
			+ df.format(calendar.get(Calendar.MINUTE))
			+ df.format(calendar.get(Calendar.SECOND));

			sDate = (mid(sDate, 5, 2)+"-"+mid(sDate, 7, 2)+" "+mid(sDate, 9, 2)+":"+mid(sDate, 11, 2)+":"+mid(sDate, 13, 2));
			//System.out.println("sDate:" + df.format(calendar.get(Calendar.HOUR_OF_DAY)) );
			return sDate;
		} catch(Exception e) {
			return sDate;
		}
	}

	//날짜변환 MM:DD HH:MM:SS
	//Return : 09-10 12:00:45
	public static String convertDate(String aStr) {
		StringBuffer tmpStr = new StringBuffer();
		if (aStr == null) {
			tmpStr.append(" ");
			return tmpStr.toString();
		}
		if (aStr.equals("")) {
			tmpStr.append(" ");
			return tmpStr.toString();
		}

		if (aStr != null) {
			tmpStr.append(mid(aStr, 5, 2)+"-"+mid(aStr, 7, 2)+" "+mid(aStr, 9, 2)+":"+mid(aStr, 11, 2)+":"+mid(aStr, 13, 2));
		}
		return tmpStr.toString();
	}

	//날짜변환 HH:MM:SS
	//Return : 12:00:45
	public static String convertTime(String aStr) {
		StringBuffer tmpStr = new StringBuffer();
		if (aStr == null) {
			tmpStr.append(" ");
			return tmpStr.toString();
		}
		if (aStr.equals("")) {
			tmpStr.append(" ");
			return tmpStr.toString();
		}

		if (aStr != null) {
			tmpStr.append(mid(aStr, 9, 2)+":"+mid(aStr, 11, 2)+":"+mid(aStr, 13, 2));
		}
		return tmpStr.toString();
	}

	/**
	 * @param aStr String
	 * @param aStartPtr int
	 * @param aCnt int
	 * @return String
	 * @throws Exception null
	 */
	public static String mid(String aStr, int aStartPtr, int aCnt) {
		StringBuffer tmpStr = new StringBuffer();
		if (aStr == null) {
			for (int i = 1; i <= aCnt; i++) {
				tmpStr.append(" ");
			}
			return tmpStr.toString();
		}
		if (aStr.equals("")) {
			for (int i = 1; i <= aCnt; i++) {
				tmpStr.append(" ");
			}
			return tmpStr.toString();
		}
		if (aStr.length() < aStartPtr) {
			for (int i = 1; i <= aCnt - aStartPtr; i++) {
				tmpStr.append(" ");
			}
			return tmpStr.toString();
		}

		int totCnt = 0;
		if (aStr != null) {
			totCnt = aStr.length() - aStartPtr;
		}
		if (aStr != null && !aStr.equals("") && (aStr.length() >= aCnt) && (aStartPtr > 0) && (totCnt >= aCnt)) {
			tmpStr.append(aStr.substring(aStartPtr - 1, aStartPtr + aCnt - 1));
		} else if (aStr != null && aStr.length() != 0 && totCnt < aCnt) {
			tmpStr.append(aStr.substring(aStartPtr - 1, aStr.length()));
			for (int i = tmpStr.length(); i < aCnt; i++) {
				tmpStr.append(" ");
			}
		} else {
			for (int i = 1; i <= aCnt; i++) {
				tmpStr.append(" ");
			}
		}
		return tmpStr.toString();
	}

	public static String left(String aStr, int aCnt) {
		StringBuffer tmpStr = new StringBuffer();
		int sPtr = 0;
		if (aStr != null && !aStr.equals("") && (aStr.length() >= aCnt) && aCnt > 0) {
			tmpStr.append(aStr.substring(aCnt));
		} else {
			if (aStr != null) {
				tmpStr.append(aStr);
				sPtr = aStr.length();
			}
			for (int i = sPtr; i < aCnt; i++) {
				tmpStr.append(" ");
			}
		}
		return tmpStr.toString();
	}

	public static String right(String aStr, int aCnt) {

		StringBuffer tmpStr = new StringBuffer();
		int sPtr = 0;
		if (aStr != null && !aStr.equals("") && (aStr.length() >= aCnt) && aCnt > 0) {
			tmpStr.append(aStr.substring(0, aCnt - 1));
		} else {
			if (aStr != null) {
				tmpStr.append(aStr);
				sPtr = aStr.length();
			}
			for (int i = sPtr; i < aCnt; i++) {
				tmpStr.append(" ");
			}
		}
		return tmpStr.toString();
	}

	/**
	 * @param aValue String
	 * @return int
	 */
	public static int string2Int(String aValue) {
		int result = 0;
		if (aValue == null || "".equals(aValue.trim())) {
			return 0;
		}

		try {
			result = (int) Double.parseDouble(aValue.trim());
		} catch (NumberFormatException e) {
			result = 0;
		}
		return result;
	}

	/**
	 * @param aValue String
	 * @return int
	 */
	public static String Int0String(String aValue) {
		String result = "0" + aValue;
		if (aValue == null || "".equals(aValue.trim())) {
			return result = "00";
		}

		try {
			if(right(aValue,1).equals("0")) {
				result = "0"+ right(aValue, 1);
			} else {
				result = aValue;
			}
		} catch (NumberFormatException e) {
			result = "00";
		}

		return result;
	}

	/**
	 * @param aStr String
	 * @return int
	 */
	public static int toInt(String aStr) {
		int result = 0;
		if (aStr == null) {
			result = 0;
		} else if (aStr.equals("")) {
			result = 0;
		} else {
			result = string2Int(String.valueOf(aStr));
		}
		return result;
	}

	/**
	 * 거리 단위 mm 를 M 로 Convert
	 *@param mmDist 미리미터
	 *return int Mdist
	 */
	public static int toMeter(int value) {
		int tmpValue = 0;
		try{
			tmpValue = (int)Math.round( ((double)value) / 1000);
		} catch(NumberFormatException e) {
			tmpValue = 0;
		}
		return tmpValue;
	}

	/**
	 * 밀리 초단위의 스트링 입력받아 시분초단위로 변환 
	 * @ param String 밀리 초
	 * @ return String ?시간?분?초
	 */
	public static String secondToHour(int tmpSS) {
		int mStandard = 60;	//1분 = 60초
		int hStandard = 3600;	//1시간 = 3600초
		StringBuffer  hmsBuf = new StringBuffer();
		int hInt=0;
		int mInt=0;
		int sInt=0;

		hInt = tmpSS / hStandard;
		mInt = (tmpSS % hStandard) / mStandard ;
		sInt = (tmpSS % hStandard) % mStandard ;
		
		if( hInt > 0 ) {
			hmsBuf.append(String.valueOf(hInt));
			hmsBuf.append("h ");
		}

		if( mInt > 0 ){
			hmsBuf.append(String.valueOf(mInt));
			hmsBuf.append("m ");
		}

		hmsBuf.append(String.valueOf(sInt));
		hmsBuf.append("s ");

		return hmsBuf.toString();
	}

	public static String secondToHour2(int tmpSS) {
		int mStandard = 60;	//1분 = 60초
		int hStandard = 3600;	//1시간 = 3600초
		StringBuffer  hmsBuf = new StringBuffer();
		int hInt=0;
		int mInt=0;
		//int sInt=0;

		hInt = tmpSS / hStandard;
		mInt = (tmpSS % hStandard) / mStandard ;
		//sInt = (tmpSS % hStandard) % mStandard ;

		if( hInt > 0 ) {
			hmsBuf.append(String.valueOf(hInt));
			hmsBuf.append("h ");
		}

		if( mInt > 0 ){
			hmsBuf.append(String.valueOf(mInt));
			hmsBuf.append("m ");
		}

		return hmsBuf.toString();
	}

	public static String formatDouble(String value) { 
		String tmpValue = "";
		if (value == null || value.trim().equals(""))
			return tmpValue;
		else
			return formatFloat(Double.parseDouble(value));
	}

	public static String formatFloat(double value) {
		DecimalFormat formatFloat =new DecimalFormat("###############.##");
		return formatFloat.format(value);
	}

	public static String formatFloat(double value, int dotUnder) {
		StringBuffer format = new StringBuffer();
		format.append("###############");

		if ( dotUnder > 0) {
			format.append(".");
		}
		for ( int i=0; i < dotUnder; i++) {
			format.append("#");
		}

		DecimalFormat formatFloat =new DecimalFormat(format.toString());
		return formatFloat.format(value);
	}

	/* 나눗셈 더블형 변환 계산*/
	public static double ModCalc(int DelayNo, int TotalNo){
		double DelayRate;
		DelayRate = (double)(((double)DelayNo / (double)TotalNo) * 100);	  
		return DelayRate;
	}

	public static String formatInt(int value) {
		DecimalFormat formatInt=new DecimalFormat("###############");
		return formatInt.format(value);
	}

	public static String formatInt(String value) {
		String tmpValue = "";
		if (value == null || value.trim().equals(""))
			return tmpValue;
		else
			return formatInt(Integer.parseInt(value));
	}
	
	public static int getIntValue(String value) {
		int tmpValue = 0;
		
		if (value==null || value.trim().equals("")) {
			return 0;
		}
		
		try {
			tmpValue = Integer.parseInt(value);
		} catch(NumberFormatException e) {
			tmpValue = 0;
		}

		return tmpValue;
	}

	// 입력 문자열을 SHA-256으로 암호화하여 해시 값을 반환하는 메서드
    public static String sha256Encrypt(String str) {
		String sha="";

		try {
			MessageDigest sh= MessageDigest.getInstance("SHA-256");
			sh.update(str.getBytes());
			byte[] byteData = sh.digest();
			StringBuilder sb = new StringBuilder();
			for (byte byteDatum : byteData) {
				sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
			}
			sha = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("암호화 에러-NoSuchAlgorithmException");
			sha = null;
		}
		return sha;
    }
    
    
    /**
     * view로 보낼 plt번호 반환 함수.
     * @param pltNo 파레트 번호
     * @return pltNo DB pltNo
     * @return result html에 보여줄 pltNo
     * 
     * */
    public static HashMap<String, String[]> getPltArray(int[] dbPltNo, String ptTp) {
       
       
       
       String[] dbPltArr = new String[dbPltNo.length];
       String[] viewPltArr = new String[dbPltNo.length];
       
       
       
       
       for(int i = 0; i < dbPltNo.length; i++) {
          
          String ptNo = Integer.toString(dbPltNo[i]);
          String viewPltNo = ptTp+String.format("%5s", ptNo).replace(" ", "0");
          
          dbPltArr[i] = ptNo;
          viewPltArr[i] = viewPltNo;
          
          System.out.println(dbPltArr[i]);
          System.out.println(viewPltArr[i]);
          
          
       }
       
       
       
       
       HashMap<String, String[]> Map = new HashMap<String, String[]>();
       
       Map.put("dbPltArr", dbPltArr);
       Map.put("viewPltArr", viewPltArr);
       
       
       
       return Map;
       
    }
    
    /**
     * view로 보낼 plt번호 반환 함수.
     * @param pltNo 파레트 번호
     * @return pltNo DB pltNo
     * @return result html에 보여줄 pltNo
     * 
     * */
    public static HashMap<String, Object> getPltNo(int dbPltNo, String ptTp) {
       
       if(dbPltNo == 0) {
          dbPltNo = 1;
       }else {
          dbPltNo += 1;
       }
       String ptNo = Integer.toString(dbPltNo);
       
       String viewPltNo = ptTp+String.format("%5s", ptNo).replace(" ", "0");
       
       HashMap<String, Object> Map = new HashMap<String, Object>();
       
       Map.put("dbPltNo", dbPltNo);
       Map.put("viewPltNo", viewPltNo);
         
       return Map;
    }
    
	/**
	 * view로 보낼 plt번호 반환 함수.
	 * 
	 * @param pltNo 파레트 번호
	 * @return pltNo DB pltNo
	 * @return result html에 보여줄 pltNo
	 * 
	 */
	public static HashMap<String, Object> getEmpPltNo(int dbPltNo) {

		if (dbPltNo == 0) {
			dbPltNo = 1;
		} else {
			dbPltNo += 1;
		}
		String ptNo = Integer.toString(dbPltNo);

		String viewPltNo =  "PT_" + ptNo;

		HashMap<String, Object> Map = new HashMap<String, Object>();

		Map.put("dbPltNo", dbPltNo);
		Map.put("viewPltNo", viewPltNo);

		return Map;
	}
    
    public static void delFile(String filePath) {
        try {
           String path = filePath; // C 드라이브 -> test폴더 -> test.txt
           File file = new File(path); // file 생성

           if(file.delete()){ // f.delete 파일 삭제에 성공하면 true, 실패하면 false
              System.out.println("파일을 삭제하였습니다");
           }else{
              System.out.println("파일 삭제에 실패하였습니다");
           }
        } catch(Exception e) {
           e.printStackTrace();
        }
     }

    
    /**
	 * 문자열을 바이트 단위로 substring하기
	 *
	 * UTF-8 기준 한글은 3바이트, 알파벳 대소문자나 숫자 및 띄어쓰기는 1바이트로 계산된다.
	 *
	 * @param str
	 * @param beginBytes
	 * @param endBytes
	 * @return
	 */
	public static String substringByBytes(String str, int beginBytes, int endBytes) {
		if (str == null || str.length() == 0) {
			return "";
		}
		// 시작 단어 바이트
		if (beginBytes < 0) {
			beginBytes = 0;
		}
		// 끝나는 단어 바이트
		if (endBytes < 1) {
			return "";
		}

		// String 길이
		int len = str.length();

		// 시작점
		int beginIndex = -1;
		int endIndex = 0;

		// 현재 바이트 위치
		int curBytes = 0;

		// subString한 글자
		String ch = null;

		// 문자열을 하나씩 검사하면서 바이트 길이 계산
		for (int i = 0; i < len; i++) {
			ch = str.substring(i, i + 1);
			int charBytes = ch.getBytes(StandardCharsets.UTF_8).length;

			// 시작점이 -1이면서 현재 바이트가 시작 바이트보다 클 경우
			if (beginIndex == -1 && curBytes + charBytes > beginBytes) {
				beginIndex = i;
			}

			// 현재 바이트가 endBytes를 초과하면 종료
			if (curBytes + charBytes > endBytes) {
				break;
			}

			curBytes += charBytes;
			endIndex = i + 1;
		}

		return str.substring(beginIndex, endIndex);
	}
    
}