import { OnInit } from "@angular/core";

export class Cliente  {

    private id_cliente:number
    private dni: string;
    private nombre: string;
    private apellidos: string;
    private fecha_nacimiento: string;
    private calle: string;
    private ciudad: string;
    private codigo_postal: string;
    private fecha_inscripcion: string;
    private email:string;
    private provincia:string;


    /**
     * Getter $dni
     * @return {string}
     */
	public get $dni(): string {
		return this.dni;
	}

    /**
     * Getter $nombre
     * @return {string}
     */
	public get $nombre(): string {
		return this.nombre;
	}

    /**
     * Getter $apellidos
     * @return {string}
     */
	public get $apellidos(): string {
		return this.apellidos;
	}

    /**
     * Getter $fecha_nacimiento
     * @return {string}
     */
	public get $fecha_nacimiento(): string {
		return this.fecha_nacimiento;
	}

    /**
     * Getter $calle
     * @return {string}
     */
	public get $calle(): string {
		return this.calle;
	}

    /**
     * Getter $ciudad
     * @return {string}
     */
	public get $ciudad(): string {
		return this.ciudad;
	}

    /**
     * Getter $codigo_postal
     * @return {string}
     */
	public get $codigo_postal(): string {
		return this.codigo_postal;
	}

    /**
     * Getter $fecha_inscripcion
     * @return {string}
     */
	public get $fecha_inscripcion(): string {
		return this.fecha_inscripcion;
	}

    /**
     * Getter $email
     * @return {string}
     */
	public get $email(): string {
		return this.email;
	}

    /**
     * Getter $provincia
     * @return {string}
     */
	public get $provincia(): string {
		return this.provincia;
	}

    /**
     * Setter $dni
     * @param {string} value
     */
	public set $dni(value: string) {
		this.dni = value;
	}

    /**
     * Setter $nombre
     * @param {string} value
     */
	public set $nombre(value: string) {
		this.nombre = value;
	}

    /**
     * Setter $apellidos
     * @param {string} value
     */
	public set $apellidos(value: string) {
		this.apellidos = value;
	}

    /**
     * Setter $fecha_nacimiento
     * @param {string} value
     */
	public set $fecha_nacimiento(value: string) {
		this.fecha_nacimiento = value;
	}

    /**
     * Setter $calle
     * @param {string} value
     */
	public set $calle(value: string) {
		this.calle = value;
	}

    /**
     * Setter $ciudad
     * @param {string} value
     */
	public set $ciudad(value: string) {
		this.ciudad = value;
	}

    /**
     * Setter $codigo_postal
     * @param {string} value
     */
	public set $codigo_postal(value: string) {
		this.codigo_postal = value;
	}

    /**
     * Setter $fecha_inscripcion
     * @param {string} value
     */
	public set $fecha_inscripcion(value: string) {
		this.fecha_inscripcion = value;
	}

    /**
     * Setter $email
     * @param {string} value
     */
	public set $email(value: string) {
		this.email = value;
	}

    /**
     * Setter $provincia
     * @param {string} value
     */
	public set $provincia(value: string) {
		this.provincia = value;
	}


	// constructor(id_cliente:number,dni:string,nombre:string,apellidos:string,fecha_nacimiento:string,calle:string,codigo_postal:string,ciudad:string,fecha_inscripcion:string,provincia:string,email:string) {
    // this.nombre = nombre;
    // this.apellidos = apellidos;
    // this.dni = dni;
    // this.fecha_nacimiento = fecha_nacimiento;
    // this.calle = calle;
    // this.codigo_postal = codigo_postal;
    // this.ciudad = ciudad;
    // this.fecha_inscripcion = fecha_inscripcion;
    // this.email = email;
    // this.provincia = provincia;
  }



  

  

  