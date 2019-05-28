package demo;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

public class ProdTest1 {

    public static void main(String[] args) throws MQClientException {
        //1、设置分组
        DefaultMQProducer producer = new DefaultMQProducer("rmq-group");
        //2、服务器集群的ip地址及端口号
        producer.setNamesrvAddr("106.52.252.113:9876;106.52.246.162:9876");
        //3、设置接口名称
        producer.setInstanceName("producer");
        //4、启动
        producer.start();
        try {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(1000); // 每秒发送一次MQ
                //new Message(String topic,String tags,byte[] body)
                Message msg = new Message("my-topic", // topic 主题名称
                        "TagA", // tag 临时值
                        ("mytopic-"+i).getBytes()// body 内容
                );
                //投递给broker
                SendResult sendResult = producer.send(msg);
                System.out.println(sendResult.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        producer.shutdown();
    }

}
