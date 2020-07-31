package webRtc;

import java.io.IOException;
import java.util.Vector;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/onlineServer")
public class OnlineServer {
	//当客户端访问onlineServer连接时候 要创建一个onlineServer的实例
	private Session session;//当前会话的session
	public static Vector<OnlineServer> clients=new Vector<OnlineServer>();
	/**
	 * @OnOpen当客户端和服务端建立连接时触发的方法
	 * @author 陈泽华
	 * @param session
	 * @date 2020-05-27 14:08:14
	 */
	@OnOpen
	public void opOpen(Session session) {
		System.out.println(session.getId()+ "open");
		this.session=session;
		clients.add(this);
	}
	@OnClose
	public void onClose() {
		clients.remove(this);
	}
	
	//客户端向服务端发送消息触发的事件
	@OnMessage
	public void onMessage(String message,Session session) {
		//群发广播消息
		for(OnlineServer client:clients) {
			try {
				System.out.println(client.session.getId());
				client.session.getBasicRemote().sendText(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		}
	}

}
