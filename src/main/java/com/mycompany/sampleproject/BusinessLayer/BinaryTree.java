/*
 * Copyright 2016 Auror.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mycompany.sampleproject.BusinessLayer;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 *
 * @author Auror
 */
public class BinaryTree {
    private final InputStream input; 
    public BinaryTree(InputStream input)
    {
        this.input = input;
        init();
    }
    
    Node root;
    Node currentNode;
    int maxDepth;
    int countEndNode;
    double varSum;
    double varDepth;
    double sumDepth;
    
    private void init()
    {
        root = new Node('/', 0);
        currentNode = root;
        maxDepth = 0;
        countEndNode = 0;
        varSum = 0;
        varDepth = 0;
        sumDepth=0;
    }
    
    public void createNode(boolean left)
    {
        if(left)
        {
            if(currentNode.left == null)
            {
                currentNode.left = new Node('0', currentNode.depth+1);
                maxDepth = currentNode.left.depth > maxDepth ? currentNode.left.depth : maxDepth;
                currentNode = root;
            }
            else
            {
                currentNode = currentNode.left;
            }
        }
        else
        {
            if(currentNode.right == null)
            {
                currentNode.right = new Node('1', currentNode.depth+1);
                maxDepth = currentNode.right.depth > maxDepth ? currentNode.right.depth : maxDepth;
                currentNode = root;
            }
            else
            {
                currentNode = currentNode.right;
            }
        }
    }
    
    public void read() throws IOException 
    {
        int c;
        while ((c = input.read()) != -1) {
            if (c == 0x0a) {
                break;
            }
        }
        while ((c = input.read()) != -1) {
            if (c == 0x3e) {
                while ((c = input.read()) != -1) {
                    if (c == 0x0a) {
                        break;
                    }
                }
            }
            if (c == 0x0a) {
                continue;
            }
            if (c == 0x4e) {
                continue;
            }
            char buffer = (char) c;
            for (int i = 0; i < 8; i++) {
                if (0 == (buffer & 0x80)) {
                    createNode(true);
                } else {
                    createNode(false);
                }
                buffer <<= 1;
            }
        }
    }
    
    public void print(PrintWriter output)
    {
        printTree(root, output);
        calcAvgDepth(root);
        calcVarDepth(root);
        output.printf("MaxDepth = "+ maxDepth +"%nAvgDepth = "+ (sumDepth/countEndNode) +"%nVarDepth = "+ varDepth);
    }
    
    public void printTree(Node current, PrintWriter output)
    {
        if(current == null) return;
        printTree(current.right, output);
        for(int i=0; i<current.depth; i++)
        {
            output.print("---");
        }
        output.printf(current.value+"("+current.depth+")%n");
        printTree(current.left, output);
    }
    
    public void calcAvgDepth(Node current)
    {
        if(current == null) return;        
        if(current.left == null && current.right == null)
        {
            countEndNode++;
            sumDepth += current.depth;
            return;
        }       
        calcAvgDepth(current.left);
        calcAvgDepth(current.right);      
    }
    
    public void calcVarSum(Node current)
    {
        if(current == null) return;
        if(current.left == null && current.right == null)
        {
            varSum += (current.depth - sumDepth/countEndNode) * 
                      (current.depth - sumDepth/countEndNode);
        }
        calcVarSum(current.left);
        calcVarSum(current.right);
    }
    
    public void calcVarDepth(Node current)
    {
        calcVarSum(current);
        if(countEndNode > 1)
        {
            varDepth = Math.sqrt(varSum/(countEndNode-1));
        }
        else
        {
            varDepth =Math.sqrt(varSum);
        }
    }
}
