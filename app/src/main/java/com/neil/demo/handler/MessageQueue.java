package com.neil.demo.handler;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageQueue {

	//通过数组的结构存储Message对象
	Message[] items;

	//入队和出队元素索引位置
	int putIndex;
	int takeIndex;

	//计数器
	int count;

	//互斥锁
	/**代码块加锁
	 * synchronized (msg) {
	 *	}
	 */
	Lock lock;
	//条件变量
	private Condition notEmpty;
	private Condition notFull;

	public MessageQueue(){
		//消息队列应该有大小限制
		this.items = new Message[50];
		this.lock = new ReentrantLock();
		this.notEmpty = lock.newCondition();
		this.notFull = lock.newCondition();
	}

	/**
	 * 加入队列(子线程运行)
	 * 生产
	 * @param msg
	 */
	public void enqueueMessage(Message msg){
		System.out.println("加入队列");

		try{
			lock.lock();
			//消息队列满了,子线程停止发送消息，阻塞
			while (count == items.length) {
				try {
					notFull.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			items[putIndex] = msg;
			//循环取值
			putIndex = (++putIndex == items.length)?0:putIndex;
			count++;

			//有新的Message对象，通知主线程
			notEmpty.signalAll();
		}finally{
			lock.unlock();
		}
	}

	/**
	 * 出队列(主线程运行)
	 * 消费
	 * @return
	 */
	public Message next(){
		Message msg = null;
		try {
			//消息队列为空,主线程Looper停止轮询,阻塞
			lock.lock();
			while (count == 0) {
				try {
					notEmpty.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			msg = items[takeIndex];
			items[takeIndex] = null;//取完值后,元素置空
			takeIndex = (++takeIndex == items.length)?0:takeIndex;
			count--;

			//使用了一个Message对象,通知子线程可以继续生产
			notFull.signalAll();
		} finally {
			lock.unlock();
		}
		return msg;
	}
}
