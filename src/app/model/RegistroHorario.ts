import { OnInit } from "@angular/core";

export class RegistroHorario  {

    private id_registrohorario:string;
    private horaentrada:string;
    private horasalida:string
    private fecha:string;
    

    /**
     * Getter $id_registrohorario
     * @return {string}
     */
	public get $id_registrohorario(): string {
		return this.id_registrohorario;
	}

    /**
     * Getter $horaentrada
     * @return {string}
     */
	public get $horaentrada(): string {
		return this.horaentrada;
	}

    /**
     * Getter $fecha
     * @return {string}
     */
	public get $fecha(): string {
		return this.fecha;
	}

    /**
     * Setter $id_registrohorario
     * @param {string} value
     */
	public set $id_registrohorario(value: string) {
		this.id_registrohorario = value;
	}

    /**
     * Setter $horaentrada
     * @param {string} value
     */
	public set $horaentrada(value: string) {
		this.horaentrada = value;
	}

    /**
     * Setter $fecha
     * @param {string} value
     */
	public set $fecha(value: string) {
		this.fecha = value;
	}

    
  }



  

  

  