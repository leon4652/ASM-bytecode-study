//package org.agent.asm_tree_api.testcode.chap07;
//import org.objectweb.asm.tree.*;
//import static org.objectweb.asm.Opcodes.*;
//
//import java.util.Iterator;
//
//public class OptimizeJumpTransformer extends MethodTransformer {
//    public OptimizeJumpTransformer(MethodTransformer mt) {
//        super(mt);
//    }
//
//    @Override
//    public void transform(MethodNode mn) {
//        InsnList insns = mn.instructions;
//        Iterator<AbstractInsnNode> i = insns.iterator();
//        while (i.hasNext()) {
//            AbstractInsnNode in = i.next();
//            if (in instanceof JumpInsnNode) {
//                LabelNode label = ((JumpInsnNode) in).label;
//                AbstractInsnNode target;
//// while target == goto l, replace label with l
//                while (true) {
//                    target = label;
//                    while (target != null && target.getOpcode() < 0) {
//                        target = target.getNext();
//                    }
//                    if (target != null && target.getOpcode() == GOTO) {
//                        label = ((JumpInsnNode) target).label;
//                    } else {
//                        break;
//                    }
//                }
//// update target
//                ((JumpInsnNode) in).label = label;
//// if possible, replace jump with target instruction
//                if (in.getOpcode() == GOTO && target != null) {
//                    int op = target.getOpcode();
//                    if ((op >= IRETURN && op <= RETURN) || op == ATHROW) {
//// replace ’in’ with clone of ’target’
//                        insns.set(in, target.clone(null));
//                    }
//                }
//            }
//        }
//        super.transform(mn);
//    }
//}
