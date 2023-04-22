package bandesal.gob.sv.responses;

public class GenericResponse {

	private Integer code;
	private String mensaje;
	
	public GenericResponse() {
		// TODO Auto-generated constructor stub
	}

	public GenericResponse(Integer code, String mensaje) {
		super();
		this.code = code;
		this.mensaje = mensaje;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
}
