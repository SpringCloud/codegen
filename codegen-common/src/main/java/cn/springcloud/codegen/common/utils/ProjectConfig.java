package cn.springcloud.codegen.common.utils;

import java.io.*;


/**
 * @author xujin
 */
public class ProjectConfig  implements Serializable {


	/**
	 * 使用序列化方式深度克隆项目配置模型
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
    public ProjectConfig deepClone() throws IOException, ClassNotFoundException {
    	ProjectConfig dc = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(this);
        oos.close();
        
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream bis = new ObjectInputStream(bais);
        dc = (ProjectConfig)bis.readObject();
        return dc;
    }
}
