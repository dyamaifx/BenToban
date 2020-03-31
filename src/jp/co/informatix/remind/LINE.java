package jp.co.informatix.remind;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.ExecutionException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.ConfirmTemplate;
import com.linecorp.bot.model.response.BotApiResponse;

public class LINE {
	//TODO LINE でリマインドする処理

	// LINEアプリ名は "bentoreminder"


	private static final String CHANNEL_ADDESS_TOKEN = "GW8CopCmF8uBfprHX/x6JxbWn9JDBe6TGC0pru3apb0MHDq+hhAep08t/EJkbc23dIgTFfT8w9gHI3BqzPWBv0Gok5SSdSA6CkNkmRWFkEWl6cQIg+qPF46Z5S1ixgMqI2yi6ELD/8RsQr1zJ9MQIwdB04t89/1O/w1cDnyilFU=";

	public void ConnectLINE() {
		final LineMessagingClient client = LineMessagingClient.builder(CHANNEL_ADDESS_TOKEN).build();

		try {
			String channelSecret = "8d439193328897e01f65943e66f610d9"; //秘密鍵
			String httpRequestBody = ""; //リクエストボディのダイジェスト値
			SecretKeySpec key = new SecretKeySpec(channelSecret.getBytes(), "HmacSHA256");
			Mac mac = Mac.getInstance("HmacSHA256"); // This can throw NoSuchAlgorithmException.
			mac.init(key);
			byte[] source = httpRequestBody.getBytes("UTF-8");
			String signature= Base64.getEncoder().encodeToString(mac.doFinal(source));


		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// 取り敢えず取り溢さず拾う事とする
			e.printStackTrace();
		}

	}

	public void replyMessage(String replyToken) {
		final LineMessagingClient client = LineMessagingClient.builder(CHANNEL_ADDESS_TOKEN).build();
		final TextMessage textMessage = new TextMessage("Hello world!");
		//final ReplyMessage replyMessage = new ReplyMessage(replyToken, textMessage);
		final BotApiResponse botApiRes;

		try {
			botApiRes = client.pushMessage(new PushMessage("", new TemplateMessage("今日はお弁当の当番だぞい！", new ConfirmTemplate("憶えてた？", new MessageAction("はい", "y"), new MessageAction("…はい", "n"))))).get();
			//botApiRes = client.replyMessage(replyMessage).get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return;
		}
		System.out.println(botApiRes);
	}
}
