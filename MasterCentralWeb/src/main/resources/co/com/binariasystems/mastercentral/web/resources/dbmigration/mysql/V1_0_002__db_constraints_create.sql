/********************************************************************************************
*					GestPymeSOC - DataBase Constraints Creation
* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
* Convencion de Nombramiento de constraints
* [ABREVIACION_TABLA]_[ABREVIACION_CAMPOS_IMPLICADOS]_[SUFIJO]
*
* Sufijos Validos:
* FOREIGN KEY:		_FK
* UNIQUE:			_UK
* CHECK:			_CK
* PRIMARY KEY:		_PK
*
* Ejemplo: Tabla SGT_USUARIOS tiene un campo llamado LOGIN_ALIAS que debe ser UNICO
*
* El constraint se llamaria asi:
* ABREVIACION TABLA: USRS
* ABREVIACION_CAMPOS_IMPLICADOS: LOGIN
* SUFIJO: _UK
*
* Resultado: USRS_LOGIN_UK
*
*********************************************************************************************/
/**************************************************************************************************************************************
* Creacion de Constraints para tabla MAT_PAISES
**************************************************************************************************************************************/
ALTER TABLE MATCENTRAL.MAT_PAISES ADD CONSTRAINT PAISES_COD_DANE_UK UNIQUE (COD_DANE);


/**************************************************************************************************************************************
* Creacion de Constraints para tabla MAT_DEPARTAMENTOS
**************************************************************************************************************************************/
ALTER TABLE MATCENTRAL.MAT_DEPARTAMENTOS ADD CONSTRAINT DEPART_IDPAIS_FK FOREIGN KEY (ID_PAIS) REFERENCES MATCENTRAL.MAT_PAISES (ID_PAIS) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE MATCENTRAL.MAT_DEPARTAMENTOS ADD CONSTRAINT DEPART_COD_UK UNIQUE (ID_PAIS,COD_DEPARTAMENTO);


/**************************************************************************************************************************************
* Creacion de Constraints para tabla MAT_CIUDADES
**************************************************************************************************************************************/
ALTER TABLE MATCENTRAL.MAT_CIUDADES ADD CONSTRAINT CIUD_IDDEPART_FK FOREIGN KEY (ID_DEPARTAMENTO) REFERENCES MATCENTRAL.MAT_DEPARTAMENTOS (ID_DEPARTAMENTO) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE MATCENTRAL.MAT_CIUDADES ADD CONSTRAINT CIUD_COD_UK UNIQUE (ID_DEPARTAMENTO,COD_CIUDAD);



/**************************************************************************************************************************************
* Creacion de Constraints para tabla MAT_BANCOS
**************************************************************************************************************************************/
ALTER TABLE MATCENTRAL.MAT_BANCOS ADD CONSTRAINT BANCO_COD_UK UNIQUE(COD_BANCO);
ALTER TABLE MATCENTRAL.MAT_BANCOS ADD CONSTRAINT BANCO_VIGENTE_CK CHECK (ES_VIGENTE IN('N','S'));

/**************************************************************************************************************************************
* Creacion de Constraints para tabla MAT_AGRUPADOR_ESTADOS
**************************************************************************************************************************************/
ALTER TABLE MATCENTRAL.MAT_AGRUPADOR_ESTADOS ADD CONSTRAINT AGRUPEST_COD_UK UNIQUE (COD_AGRUPADOR);


/**************************************************************************************************************************************
* Creacion de Constraints para tabla MAT_ESTADOS
**************************************************************************************************************************************/
ALTER TABLE MATCENTRAL.MAT_ESTADOS ADD CONSTRAINT ESTD_AGRUPADOR_FK FOREIGN KEY (ID_AGRUPADOR) REFERENCES MATCENTRAL.MAT_AGRUPADOR_ESTADOS (ID_AGRUPADOR) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE MATCENTRAL.MAT_ESTADOS ADD CONSTRAINT ESTD_COD_UK UNIQUE (COD_ESTADO);
ALTER TABLE MATCENTRAL.MAT_ESTADOS ADD CONSTRAINT ESTD_ACTIVO_CK CHECK (ES_ACTIVO IN('N','S'));


/**************************************************************************************************************************************
* Creacion de Constraints para tabla MAT_MAESTRAS
**************************************************************************************************************************************/
ALTER TABLE MATCENTRAL.MAT_MAESTRAS ADD CONSTRAINT MAESTR_COD_UK UNIQUE (COD_MAESTRA);


/**************************************************************************************************************************************
* Creacion de Constraints para tabla MAT_DATOS_MAESTRAS
**************************************************************************************************************************************/
ALTER TABLE MATCENTRAL.MAT_DATOS_MAESTRAS ADD CONSTRAINT DAT_MAESTR_MAESTRA_FK FOREIGN KEY (ID_MAESTRA) REFERENCES MATCENTRAL.MAT_MAESTRAS (ID_MAESTRA) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE MATCENTRAL.MAT_DATOS_MAESTRAS ADD CONSTRAINT DAT_MAESTR_COD_UK UNIQUE (ID_MAESTRA,  COD_DATO_MAESTRA);


/**************************************************************************************************************************************
* Creacion de Constraints para tabla MAT_TIPOS_CONTRIBUYENTE
**************************************************************************************************************************************/
ALTER TABLE MATCENTRAL.MAT_TIPOS_CONTRIBUYENTE ADD CONSTRAINT TIPO_CONTRIB_COD_UK UNIQUE (COD_TIPO_CONTRIBUYENTE);
ALTER TABLE MATCENTRAL.MAT_TIPOS_CONTRIBUYENTE ADD CONSTRAINT TIPO_CONTRIB_RRENTA_APLIC_CK CHECK (RETEFUENTE_RENTA_APLICABLE IN('N','S'));
ALTER TABLE MATCENTRAL.MAT_TIPOS_CONTRIBUYENTE ADD CONSTRAINT TIPO_CONTRIB_RIVA_APLIC_CK CHECK (RETEFUENTE_IVA_APLICABLE IN('N','S'));
ALTER TABLE MATCENTRAL.MAT_TIPOS_CONTRIBUYENTE ADD CONSTRAINT TIPO_CONTRIB_RICA_APLIC_CK CHECK (RETEFUENTE_ICA_APLICABLE IN('N','S'));
ALTER TABLE MATCENTRAL.MAT_TIPOS_CONTRIBUYENTE ADD CONSTRAINT TIPO_CONTRIB_RCREE_APLIC_CK CHECK (RETEFUENTE_CRE_APLICABLE IN('N','S'));
ALTER TABLE MATCENTRAL.MAT_TIPOS_CONTRIBUYENTE ADD CONSTRAINT TIPO_CONTRIB_DCLRENTA_APLIC_CK CHECK (DECLARA_RENTA IN('N','S'));
ALTER TABLE MATCENTRAL.MAT_TIPOS_CONTRIBUYENTE ADD CONSTRAINT TIPO_CONTRIB_DCLIVA_APLIC_CK CHECK (DECLARA_IVA IN('N','S'));
ALTER TABLE MATCENTRAL.MAT_TIPOS_CONTRIBUYENTE ADD CONSTRAINT TIPO_CONTRIB_DCLICA_APLIC_CK CHECK (DECLARA_ICA IN('N','S'));
ALTER TABLE MATCENTRAL.MAT_TIPOS_CONTRIBUYENTE ADD CONSTRAINT TIPO_CONTRIB_DCLCREE_APLIC_CK CHECK (DECLARA_CREE IN('N','S'));


/**************************************************************************************************************************************
* Creacion de Constraints para tabla MAT_GRUPOS_TARIFA_ACTI_ECONOM
**************************************************************************************************************************************/
ALTER TABLE MATCENTRAL.MAT_GRUPOS_TARIFA_ACTI_ECONOM ADD CONSTRAINT GRUPTARIF_COD_UK UNIQUE (COD_GRUPO_TARIFARIO);
ALTER TABLE MATCENTRAL.MAT_GRUPOS_TARIFA_ACTI_ECONOM ADD CONSTRAINT GRUPTARIF_RUBRO_CK CHECK (COD_RUBRO_ECONOMICO IN('INDUSTRIAL', 'COMERCIAL', 'SERVICIOS', 'FINANCIERO', 'EXCENTO'));


/**************************************************************************************************************************************
* Creacion de Constraints para tabla MAT_ACTIVIDADES_ECONOMICAS
**************************************************************************************************************************************/
ALTER TABLE MATCENTRAL.MAT_ACTIVIDADES_ECONOMICAS ADD CONSTRAINT ACTI_ECON_COD_UK UNIQUE (COD_ACTIVIDAD_CIIU);
ALTER TABLE MATCENTRAL.MAT_ACTIVIDADES_ECONOMICAS ADD CONSTRAINT ACTI_ECON_GRUPO_TAR_FK FOREIGN KEY (ID_GRUPO_TARIFARIO) REFERENCES MATCENTRAL.MAT_GRUPOS_TARIFA_ACTI_ECONOM (ID_GRUPO_TARIFARIO) ON UPDATE NO ACTION ON DELETE NO ACTION;


/**************************************************************************************************************************************
* Creacion de Constraints para tabla MAT_TIPOS_IDENTIFICACION
**************************************************************************************************************************************/
ALTER TABLE MATCENTRAL.MAT_TIPOS_IDENTIFICACION ADD CONSTRAINT TIPO_IDENT_COD_UK UNIQUE (COD_TIPO_IDENTIFICACION);
ALTER TABLE MATCENTRAL.MAT_TIPOS_IDENTIFICACION ADD CONSTRAINT TIPO_IDENT_NOMBRE_UK UNIQUE (NOMBRE_CORTO);



/**************************************************************************************************************************************
* Creacion de Constraints para tabla MAT_GRUPOS_EMPRESARIALES
**************************************************************************************************************************************/
ALTER TABLE MATCENTRAL.MAT_GRUPOS_EMPRESARIALES ADD CONSTRAINT GRUP_EMPRE_NIT_UK UNIQUE (IDENTIFICACION_TRIBUTARIA);
ALTER TABLE MATCENTRAL.MAT_GRUPOS_EMPRESARIALES ADD CONSTRAINT GRUP_EMPRE_CIUD_FK FOREIGN KEY (ID_CIUDAD) REFERENCES MATCENTRAL.MAT_CIUDADES (ID_CIUDAD) ON UPDATE NO ACTION ON DELETE NO ACTION;


/**************************************************************************************************************************************
* Creacion de Constraints para tabla MAT_EMPRESAS
**************************************************************************************************************************************/
ALTER TABLE MATCENTRAL.MAT_EMPRESAS ADD CONSTRAINT EMPRE_NIT_UK UNIQUE (IDENTIFICACION_TRIBUTARIA);
ALTER TABLE MATCENTRAL.MAT_EMPRESAS ADD CONSTRAINT EMPRE_TIPOPERIO_CK CHECK (COD_TIPO_PERIODO_NOMINA IN('SEMANAL','QUINCENAL', 'MENSUAL'));
ALTER TABLE MATCENTRAL.MAT_EMPRESAS ADD CONSTRAINT EMPRE_ID_GRUPO_FK FOREIGN KEY (ID_GRUPO_EMPRESARIAL) REFERENCES MATCENTRAL.MAT_GRUPOS_EMPRESARIALES (ID_GRUPO_EMPRESARIAL) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE MATCENTRAL.MAT_EMPRESAS ADD CONSTRAINT EMPRE_CIUD_FK FOREIGN KEY (ID_CIUDAD) REFERENCES MATCENTRAL.MAT_CIUDADES (ID_CIUDAD) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE MATCENTRAL.MAT_EMPRESAS ADD CONSTRAINT EMPRE_TIPO_CONTR_FK FOREIGN KEY (ID_TIPO_CONTRIBUYENTE) REFERENCES MATCENTRAL.MAT_TIPOS_CONTRIBUYENTE (ID_TIPO_CONTRIBUYENTE) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE MATCENTRAL.MAT_EMPRESAS ADD CONSTRAINT EMPRE_ACT_ECONOM_FK FOREIGN KEY (ID_ACTIVIDAD_ECONOMICA) REFERENCES MATCENTRAL.MAT_ACTIVIDADES_ECONOMICAS (ID_ACTIVIDAD_ECONOMICA) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE MATCENTRAL.MAT_EMPRESAS ADD CONSTRAINT EMPRE_BANCO_FK FOREIGN KEY (ID_BANCO_CONVENIO_NOMINA) REFERENCES MATCENTRAL.MAT_BANCOS (ID_BANCO) ON UPDATE NO ACTION ON DELETE NO ACTION;


/**************************************************************************************************************************************
* Creacion de Constraints para tabla MAT_CONCEPTOS_PAGO
**************************************************************************************************************************************/
ALTER TABLE MATCENTRAL.MAT_CONCEPTOS_PAGO ADD CONSTRAINT COCEPTS_APLICEMPL_CK CHECK (APLICA_EMPLEADOS IN('N','S'));
ALTER TABLE MATCENTRAL.MAT_CONCEPTOS_PAGO ADD CONSTRAINT COCEPTS_APLICPROV_CK CHECK (APLICA_PROVEEDORES IN('N','S'));


/**************************************************************************************************************************************
* Creacion de Constraints para tabla MAT_CONFIG_RETENCIONES
**************************************************************************************************************************************/
ALTER TABLE MATCENTRAL.MAT_CONFIG_RETENCIONES ADD CONSTRAINT CONFRET_CONCEPTO_FK FOREIGN KEY (ID_CONCEPTO_PAGO) REFERENCES MATCENTRAL.MAT_CONCEPTOS_PAGO (ID_CONCEPTO_PAGO) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE MATCENTRAL.MAT_CONFIG_RETENCIONES ADD CONSTRAINT CONFRET_TIPO_PERS_CK CHECK (COD_TIPO_PERSONA IN('PERSONA_NATURAL','PERSONA_JURIDICA'));
ALTER TABLE MATCENTRAL.MAT_CONFIG_RETENCIONES ADD CONSTRAINT CONFRET_TIPO_RETENC_CK CHECK (COD_TIPO_PERSONA IN('RETEFUENTE_RENTA', 'RETEFUENTE_IVA', 'RETEFUENTE_ICA', 'RETEFUENTE_CREE'));


/**************************************************************************************************************************************
* Creacion de Constraints para tabla MAT_PARAMETROS_CONFIG
**************************************************************************************************************************************/
ALTER TABLE MATCENTRAL.MAT_PARAMETROS_CONFIG ADD CONSTRAINT PARAMCONF_CODCOF_CODAPP_UK UNIQUE(COD_PARAM_CONFIG,COD_APLICACION);
/*ALTER TABLE MATCENTRAL.MAT_PARAMETROS_CONFIG ADD CONSTRAINT PARAMCONF_CODAPP_CK CHECK (COD_APLICACION IN('GESTPYMESOC','ORION'));*/


/**************************************************************************************************************************************
* Creacion de Constraints para tabla MAT_EPSS
**************************************************************************************************************************************/
ALTER TABLE MATCENTRAL.MAT_EPSS ADD CONSTRAINT EPS_COD_UK UNIQUE(COD_EPS);
ALTER TABLE MATCENTRAL.MAT_EPSS ADD CONSTRAINT EPS_VIGENTE_CK CHECK (ES_VIGENTE IN('N','S'));


/**************************************************************************************************************************************
* Creacion de Constraints para tabla MAT_AFPS
**************************************************************************************************************************************/
ALTER TABLE MATCENTRAL.MAT_AFPS ADD CONSTRAINT AFP_COD_UK UNIQUE(COD_AFP);
ALTER TABLE MATCENTRAL.MAT_AFPS ADD CONSTRAINT AFP_VIGENTE_CK CHECK (ES_VIGENTE IN('N','S'));


/**************************************************************************************************************************************
* Creacion de Constraints para tabla MAT_ARLS
**************************************************************************************************************************************/
ALTER TABLE MATCENTRAL.MAT_ARLS ADD CONSTRAINT ARL_COD_UK UNIQUE(COD_ARL);
ALTER TABLE MATCENTRAL.MAT_ARLS ADD CONSTRAINT ARL_VIGENTE_CK CHECK (ES_VIGENTE IN('N','S'));


/**************************************************************************************************************************************
* Creacion de Constraints para tabla MAT_CCFS
**************************************************************************************************************************************/
ALTER TABLE MATCENTRAL.MAT_CCFS ADD CONSTRAINT CCF_COD_UK UNIQUE(COD_CCF);
ALTER TABLE MATCENTRAL.MAT_CCFS ADD CONSTRAINT CCF_VIGENTE_CK CHECK (ES_VIGENTE IN('N','S'));


