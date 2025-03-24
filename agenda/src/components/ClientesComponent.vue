<template>
    <v-dialog v-model="dialogClientes" max-width="650px">
        <v-card style="border-radius: 25px;">
            <v-card-title style="background-color: rgb(24 103 192);">
                <span class="text-h6" style="color: white;">{{ dialogClientesTitulo }}</span>
            </v-card-title>
            <v-card-text>
                <v-form ref="formCliente">
                    <v-text-field v-model="formCliente.nome" label="Nome" :rules="[rules.required]"></v-text-field>
                    <v-text-field v-model="formCliente.cpf" label="CPF" :rules="[rules.required]"></v-text-field>
                    <v-text-field v-model="formCliente.data_nasc" type="date" label="Data de Nascimento"></v-text-field>
                    <v-text-field v-model="formCliente.endereco" label="Endereço"></v-text-field>
                </v-form>
            </v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn @click="fecharDialogClientes">Cancelar</v-btn>
                <v-btn @click="salvarCliente">Salvar</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>

    <v-dialog v-model="dialogContatos">
        <v-card style="border-radius: 25px;">
            <v-card-title  style="background-color: rgb(24 103 192);">
                <span class="text-h6" style="color: white;">Contatos</span>
            </v-card-title>
            <v-card-text>
                <v-row style="margin-bottom: 10px; margin-right: 10px;" >
                    <v-spacer></v-spacer>
                    <v-btn color="primary" style="border-radius: 10px;" @click="abrirDialogAdicionarContato">Adicionar um Contato</v-btn>
                </v-row>
                <v-data-table
                    :headers="headersContatos"
                    :items="contatos"
                    item-value="id"
                    class="elevation-1 mt-4"
                    style="text-align: center;"
                >
                    <template v-slot:body="{ items }">
                        <tr v-for="contato in items" :key="contato.id">
                            <td>{{ contato.tipoContato }}</td>
                            <td>{{ contato.valorContato }}</td>
                            <td>{{ contato.observacao }}</td>
                            <td>
                                <v-btn icon color="primary" size="small" style="margin-right: 5px;" @click="editarContato(contato)">
                                    <v-icon>mdi-pencil</v-icon>
                                </v-btn>
                                <v-btn icon color="red" size="small" style="margin-right: 5px;" @click="deletarContato(contato.id)">
                                    <v-icon>mdi-delete</v-icon>
                                </v-btn>
                            </td>
                        </tr>
                    </template>
                </v-data-table>
            </v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn text @click="fecharDialogContatos">Fechar</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>

    <v-dialog v-model="dialogAddContato" max-width="650px">
        <v-card style="border-radius: 25px;">
            <v-card-title style="background-color: rgb(24 103 192);">
                <span class="text-h6" style="color: white;">{{ dialogContatoTitulo }}</span>
            </v-card-title>
            <v-card-text>
                <v-form ref="formContato">
                    <v-select v-model="formContato.tipoContato" :items="['Telefone', 'Email']" label="Tipo de Contato" required></v-select>
                    <v-text-field v-model="formContato.valorContato" label="Valor do Contato" required></v-text-field>
                    <v-textarea v-model="formContato.observacao" label="Observação"></v-textarea>
                </v-form>
            </v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn text @click="fecharDialogContato">Fechar</v-btn>
                <v-btn color="primary" @click="salvarContato">Salvar</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>

    <v-card>
        <v-card-title>Clientes</v-card-title>
        <v-card-text>
            <v-row style="margin-bottom: 10px; margin-right: 10px;" >
                <v-spacer></v-spacer>
                <v-btn color="primary" style="border-radius: 10px;" @click="abrirDialogAdicionar">Adicionar Cliente</v-btn>
            </v-row>
            <v-data-table
                :headers="headers"
                :items="filteredClientes"
                item-value="id"
                class="elevation-1"
                style="text-align: center;"
            >
                <template v-slot:top>
                    <v-text-field v-model="search" label="Pesquisar..." class="mx-4"></v-text-field>
                </template>
                <template v-slot:item="{ item }">
                    <tr>
                        <td>{{ item.nome }}</td>
                        <td>{{ item.cpf }}</td>
                        <td>{{ item.data_nasc }}</td>
                        <td>{{ item.endereco }}</td>
                        <td>
                            <v-btn icon color="primary" size="small" style="margin-right: 5px;" @click="abrirDialogContatos(item)">
                                <v-icon>mdi-contacts</v-icon>
                            </v-btn>
                            <v-btn icon color="primary" size="small" style="margin-right: 5px;" @click="editarCliente(item)">
                                <v-icon>mdi-pencil</v-icon>
                            </v-btn>
                            <v-btn icon color="red" size="small" style="margin-right: 5px;" @click="deletarCliente(item.id)">
                                <v-icon>mdi-delete</v-icon>
                            </v-btn>
                        </td>
                    </tr>
                </template>
            </v-data-table>
        </v-card-text>
    </v-card>
</template>
  
<script>
    export default {
        data() {
            return {
                search: "",
                headers: [
                    { title: "Nome", align: 'center' , value: "nome"},
                    { title: "CPF", align: 'center', value: "cpf" },
                    { title: "Data de Nascimento", align: 'center', value: "data_nasc" },
                    { title: "Endereço", align: 'center', value: "endereco" },
                    { title: "Ações", align: 'center', value: "acoes", sortable: false },
                ],
                headersContatos: [
                    { title: "Tipo de Contato", align: 'center', value: "tipoContato"},
                    { title: "Valor do Contato", align: 'center', value: "valorContato"},
                    { title: "Observação", align: 'center', value: "observacao"},
                    { title: "Açoes", align: 'center', value: "acoes", sortable: false},
                ],
                clientes: [],
                contatos: [],
                
                dialogClientes: false,
                dialogContatos: false,
                dialogAddContato: false,
                dialogTitulo: "",
                dialogContatoTitulo: "",
                formCliente: {
                    id: null,
                    nome: "",
                    cpf: "",
                    data_nasc: "",
                    endereco: "",
                },
                formContato: {
                    id: null,
                    tipoContato: "",
                    valorContato: "",
                    observacao: "",
                },
                clienteSelecionado: null,
                
                rules: {
                    required: value => !!value || 'Campo Obrigatório!',
                },
            };
        },

        computed: {
            filteredClientes() {
                // Filtro que retorna somente os clientes que correspondem à busca
                return this.clientes.filter((cliente) => {
                    const query = this.search.toLowerCase();
                    return (
                    cliente.nome.toLowerCase().includes(query) ||
                    cliente.cpf.toLowerCase().includes(query) ||
                    cliente.data_nasc.toLowerCase().includes(query) ||
                    cliente.endereco.toLowerCase().includes(query)
                    );
                });
            },
        },

        methods: {
            async fetchClientes() {
                try {
                    const response = await fetch("http://localhost:8081/api/clientes");
                    this.clientes = await response.json();
                } catch (error) {
                    console.error("Erro ao buscar clientes:", error);
                }
            },

            async fetchContatos(clienteId) {
                try {
                    const response = await fetch(`http://localhost:8081/api/contatos/cliente/${clienteId}`);
                    this.contatos = await response.json();
                } catch (error) {
                    console.error("Erro ao buscar contatos:", error);
  }
            },

            abrirDialogAdicionar() {
                this.dialogClientesTitulo = "Adicionar Cliente";
                this.formCliente = {
                    id: null,
                    nome: "",
                    cpf: "",
                    data_nasc: "",
                    endereco: "",
                };
                this.dialogClientes = true;
            },

            fecharDialogClientes() {
                this.dialogClientes = false;
            },

            editarCliente(cliente) {
                this.dialogClientesTitulo = "Editar Cliente";
                this.formCliente = { ...cliente }; // Preenche os campos com os dados do cliente
                this.dialogClientes = true;
            },

            async salvarCliente() {
                const url = this.formCliente.id
                ? `http://localhost:8081/api/clientes/${this.formCliente.id}` // Atualizar cliente
                : "http://localhost:8081/api/clientes"; // Adicionar novo cliente

                const method = this.formCliente.id ? "PUT" : "POST";

                await fetch(url, {
                    method,
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(this.formCliente),
                });

                this.dialogClientes = false;
                this.fetchClientes(); // Atualiza a lista de clientes
            },

            deletarCliente(id) {
                var result = confirm("Deseja apagar os dados do cliente?")
                if(result) {
                    fetch(`http://localhost:8081/api/clientes/${id}`, {
                        method: "DELETE",
                    }).then(() => this.fetchClientes());
                }
            },
            
            async abrirDialogContatos(cliente) {
                this.clienteSelecionado = cliente; // Define o cliente selecionado
                try {
                    const response = await fetch(`http://localhost:8081/api/contatos/cliente/${cliente.id}`);
                    if (!response.ok) {
                        throw new Error("Erro ao buscar contatos");
                    }
                    this.contatos = await response.json();
                    
                    // Verifica se a lista de contatos está vazia
                    if (!this.contatos || this.contatos.length === 0) {
                        this.contatos = []; // Define como uma lista vazia para evitar erros
                    }

                    this.dialogContatos = true;
                } catch (error) {
                    console.error("Erro ao carregar contatos:", error);
                    alert("Erro ao carregar os contatos do cliente. Tente novamente.");
                    this.contatos = []; // Garante que contatos seja inicializado
                    this.dialogContatos = true; // Abre o dialog vazio para cadastro
                }
            },

            abrirDialogAdicionarContato() {
                this.dialogContatoTitulo = "Adicionar Contato"
                this.formContato = {
                    id: null,
                    tipoContato: "",
                    valorContato: "",
                    observacao: "",
                };
                this.dialogAddContato = true;
            },

            editarContato(contato) {
                this.dialogContatoTitulo = "Editar Contato";
                this.formContato = { ...contato };
                this.dialogAddContato = true
            },

            fecharDialogContatos() {
                this.dialogContatos = false;
            },

            fecharDialogContato() {
                this.dialogAddContato = false;
            },

            async salvarContato() {
                try {
                    const url = this.formContato.id
                        ? `http://localhost:8081/api/contatos/${this.formContato.id}`
                        : `http://localhost:8081/api/contatos/cliente/${this.clienteSelecionado.id}`;
                    const method = this.formContato.id ? "PUT" : "POST";

                    await fetch(url, {
                        method,
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify(this.formContato),
                    });

                    this.dialogAddContato = false;
                    await this.fetchContatos(this.clienteSelecionado.id);
                } catch (error) {
                    console.error("Erro ao salvar o contato:", error);
                }
            },

            async deletarContato(id) {
                var result = confirm("Deseja apagar este contato?")
                if(result) {
                    await fetch(`http://localhost:8081/api/contatos/${id}`, {
                        method: "DELETE",
                    });
                    this.fetchContatos(this.clienteSelecionado.id);
                }
            },
        },
        mounted() {
            this.fetchClientes();
        },
    };
</script>