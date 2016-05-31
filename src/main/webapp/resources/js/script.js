var $jquery11 = jQuery.noConflict();

Util = {
	/** Carrega as funções principais do sistema */
	carregar : function() {
		setTimeout(function() {
			Util.subirPagina();
			Util.carregarMascaras();
		}, 100);
	},

	/** Posiciona a página ao topo, caso exista mensagens de erro. */
	subirPagina : function() {
		/** Caso ocorra mensagem de erro, o scrollTop receberá o Valor 0. */
		if ($('.ui-messages-error').text() || $('.ui-messages-info').text()) {
			$('.breadcrumb').animate( {
				scrollTop: 0 
			}, 1000);

			Util.destacarCamposObrigatorios();
		}
	},
	
	/** Destaca os campos obrigatórios que não foram preenchidos. */
	destacarCamposObrigatorios : function() {
		if ($('.ui-messages-error').text()) {
			$('label.required').each(function() {
				var element = $('#' + $(this).attr('for'));
				if (element.is('span')) {
					element = element.children('input');
				}
				
				if (element.val().length == 0) {
					$(element).addClass('input-required-error');
				}

				$(element).change(function() {
					if (element.val().length == 0) {
						$(element).addClass('input-required-error');
					} else {
						$(element).removeClass('input-required-error');
					}
				});
			});
		}
	},
	
	/** Carrega as principais máscaras do sistema. */
	carregarMascaras : function() {
		$('.mask-telefone').mask('(99) 9999-9999?9').focusout(function (event) {  
	        var target, phone, element;  
	        target = (event.currentTarget) ? event.currentTarget : event.srcElement;  
	        phone = target.value.replace(/\D/g, '');
	        element = $(target);  
	        element.unmask();  
	        if (phone.length > 10) {  
	            element.mask('(99) 99999-999?9');  
	        } else {  
	            element.mask('(99) 9999-9999?9');  
	        }  
	    });

		$('.mask-cpf').mask('999.999.999-99');
		$('.mask-cnpj').mask('99.999.999/9999-99')
		$('.mask-cep').mask('99999-999');
	},
};

/**
 * Permite somente numeros e as teclas 13,0 e 8  13 -> enter; 08 BackSpace; 
 * 
 * @param e
 * @returns {Boolean}
 */
function somenteNumero(e) {
	var tecla = (window.event) ? event.keyCode : e.which;   
	if ((tecla > 47 && tecla < 58)) {
		return true;
	} else {
		if (tecla == 8 || tecla == 0 || tecla == 13) {
			return true;
		} else {
			return false;
		}
	}
}