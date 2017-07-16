package com.google.engedu.ghost;

import java.util.HashMap;
import java.util.Random;


public class TrieNode {
    private HashMap<Character, TrieNode> children;
    private boolean isWord;

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {

        HashMap<Character,TrieNode> current = children;

        for (int i= 0 ; i<s.length(); i++){
            char c = s.charAt(i);
            TrieNode trieNode;
            if (current.containsKey(c)){
                trieNode = current.get(c);
            }
            else {
                trieNode = new TrieNode();
            }
            current.put(c,trieNode);
            current = trieNode.children;

            if (i == s.length() -1){
                trieNode.isWord = true;
            }
        }
    }

    public boolean isWord(String s) {

        TrieNode trieNode = searchNode(s);

        if(trieNode != null && trieNode.isWord){
            return true;
        }
        else {
            return false;
        }
    }

    public TrieNode searchNode(String prefix){
        TrieNode trieNode = null;

        HashMap<Character, TrieNode>current = children;

        for (int i = 0 ; i<prefix.length(); i++){
            char c = prefix.charAt(i);

            if(current.containsKey(c)){
                trieNode = current.get(c);
                current = trieNode.children;
            }
            else {
                return null;
            }
        }

        return trieNode;
    }

    public String getAnyWordStartingWith(String s) {

        String result =  null;

        TrieNode trieNode = searchNode(s);
        HashMap<Character,TrieNode> current;

        if (trieNode == null){
            return null;
        }
        else {
            result = s;
            while (!trieNode.isWord){
                current = trieNode.children;
                int i = new Random().nextInt(current.keySet().size());
                char next = (Character) current.keySet().toArray()[i];

                result = result + next;
                trieNode = current.get(next);
            }
        }

        return result;
    }

    public String getGoodWordStartingWith(String s) {
        return null;
    }
}
