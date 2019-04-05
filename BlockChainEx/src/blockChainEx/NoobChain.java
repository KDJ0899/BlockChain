package blockChainEx;

import java.util.ArrayList;

import com.google.gson.GsonBuilder;
/**
 * 
  * @FileName : NoobChain.java
  * @Project : blockChainEx
  * @Date : 2019. 4. 5. 
  * @Author : Kim DongJin
  * @Comment :
 */
public class NoobChain {
	
	public static ArrayList<Block> blockChain = new ArrayList<Block>();
	public static int difficulty = 5;
	
	public static void main(String[] args) {
//		Block firstBlock = new Block("Hi im the first block", "0");
//		System.out.println("Hash for block 1: "+firstBlock.hash);
//		
//		Block secondBlock = new Block("i'm the second block", firstBlock.hash);
//		System.out.println("Hash for block 2: "+secondBlock.hash);
//		
//		Block thirdBlock = new Block("i'm the third block", secondBlock.hash);
//		System.out.println("Hash for block 3: "+thirdBlock.hash);
		
		blockChain.add(new Block("Hi im the first block", "0"));
		System.out.println("Trying to Mine block 1...");
		blockChain.get(0).mineBlock(difficulty);
		
		blockChain.add(new Block("im the second block",blockChain.get(blockChain.size()-1).hash));
		System.out.println("Trying to Mine block 2...");
		blockChain.get(1).mineBlock(difficulty);
		
		blockChain.add(new Block("im the third block",blockChain.get(blockChain.size()-1).hash));
		System.out.println("Trying to Mine block 3...");
		blockChain.get(2).mineBlock(difficulty);
		
		System.out.println("\nBlockschain is Valid: "+isChainValid());
		
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockChain);
		System.out.println(blockchainJson);
	}
	
	public static Boolean isChainValid() {
		Block nowBlock;
		Block preBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		
		for(int i=1; i<blockChain.size(); i++) {
			nowBlock=blockChain.get(i);
			preBlock=blockChain.get(i-1);
			
			if(!nowBlock.hash.equals(nowBlock.calculateHash())) {
				System.out.println("Current Hashes not equal");
				return false;
			}
			
			if(!preBlock.hash.equals(nowBlock.previousHash)) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
			
			if(!nowBlock.hash.substring(0,difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				return false;
			}
		}
		
		return true;
	}
}
