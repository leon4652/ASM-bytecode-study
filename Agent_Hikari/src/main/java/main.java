import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.sch.asm_tree_api.code.AddLogger;
import org.sch.util.CodePrinter;

import java.io.IOException;

import static org.objectweb.asm.Opcodes.ACC_PRIVATE;


public class main {
    public static void main(String[] args) throws IOException {
        // 클래스 파일 읽기
        try {

            ClassNode cn = new ClassNode();
            cn.name = "TEST";
            cn.methods.add(new MethodNode(ACC_PRIVATE, "executeTest", "(I)V", null,null));

        AddLogger addLogger = new AddLogger(cn);

            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            cn.accept(cw);


            byte[] modified = cw.toByteArray();
            CodePrinter.printClass(modified, "test", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
