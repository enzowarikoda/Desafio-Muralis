<template>
  <v-app>
    <v-container>
        <v-card  style="border-radius: 25px;">
            <v-toolbar color="primary" dark>
                <v-toolbar-title>Gestão Agenda de Clientes</v-toolbar-title>
            </v-toolbar>
            <v-tabs v-model="tab">
                <v-tab>Clientes</v-tab>
            </v-tabs>

            <v-tabs-items v-model="tab">
                <!-- Tela de Clientes -->
                <v-tab-item>
                    <clientes-component />
                </v-tab-item>
            </v-tabs-items>
        </v-card>
    </v-container>
  </v-app>
</template>

<script>
import ClientesComponent from "./components/ClientesComponent.vue";

export default {
  components: {
    ClientesComponent,
  },

  data() {
    return {
        tab: 0,
    }
  },
}
</script>
