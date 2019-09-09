package blockChainEx;

import java.util.ArrayList;
import java.util.Date;

/**
 * 
  * @FileName : Block.java
  * @Project : blockChainEx
  * @Date : 2019. 4. 5. 
  * @Author : Kim DongJin
  * @Comment :
 */
public class Block {
	public String hash;
	public String previousHash;
	private String merkleRoot;
	public ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	private long timeStamp;
	private int nonce;
	
	public Block(String previousHash) {
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		
		this.hash = calculateHash();
	}
	
	public String calculateHash(){  //해쉬 함수 계산(그 전 블록 + 생산하는 시간 + 현재까지 채굴시도한 수 + 머클트리 루트)
		String calculatehash= StringUtil.applySha256(
				previousHash+
				Long.toString(timeStamp)+
				Integer.toString(nonce)+
				merkleRoot);
		return calculatehash;
	}
	
	public void mineBlock(int difficulty) {
		merkleRoot = StringUtil.getMerkleRoot(transactions);
		String target = new String(new char[difficulty]).replace('\0', '0'); // 정해진 difficulty의 수만큼 해쉬코드에 0이 있는가 확인 하는 작업
		while(!hash.substring(0,difficulty).equals(target)) {
			nonce++;
			hash=calculateHash();
		}
		System.out.println("Block Mined!! : "+hash);
	}
	
	//Add transactions to this block
	public boolean addTransaction(Transaction transaction) {  // 거래 내역 붙이는 과정
		//process transaction and check if valid, unless block is genesis block then ignore.
		if(transaction == null) return false;		
		if((previousHash != "0")) {
			if((transaction.processTransaction() != true)) {
				System.out.println("Transaction failed to process. Discarded.");
				return false;
			}
		}
		transactions.add(transaction);
		System.out.println("Transaction Successfully added to Block");
		return true;
	}


}
