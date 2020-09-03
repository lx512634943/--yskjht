<template>
  <Drawer
    title="角色分配"
    :width="260"
    :mask="false"
    :closable="true"
    :draggable="true"
    placement="left"
    :value="value"
    @input="$emit('input', $event)"
  >
    <div style="height:inherit; overflow:auto; margin-bottom:10px">
        <Tree ref="tree" :data="data" show-checkbox></Tree>
    </div>
    <Button type="primary" @click="getCheckedRoles" long>提交</Button>
  </Drawer>
</template>
<script>
import { roleListByUserId } from "@/api/user";
import { MyTreeTools } from "@/libs/util";

export default {
  name: "DrawerPage",
  props: {
    value: {
      type: Boolean,
      default: false
    },
    userId: {
      type: String,
      default: null
    }
  },
  data() {
    return {
      data: []
    };
  },
  methods: {
    getRoleListByUserId(userId) {
      if (userId) {
        roleListByUserId({ id: userId }).then(res => {
          if (res) {
            this.data = new MyTreeTools().toTreeData(res, function(node) {
                node.title = node.text
                node.expand = true
                if (node.checked === 'false') {
                    node.checked = false
                } else {
                    node.checked = true
                }
            });
          }
        });
      }
    },
    getCheckedRoles () {
      //获取角色ids
      let roles = this.$refs.tree.getCheckedNodes().filter(item => item.TYPE === 1)
      let roleIds = '';
      for(let i=0; i<roles.length; i++){
    	   roleIds += roles[i].id+",";
      }
      this.$emit('on-save-roles', roleIds)

    }
  },
  watch: {
    userId: function (val) {
      this.getRoleListByUserId(val);
    }
  },
  mounted() {
    this.getRoleListByUserId(this.userId);
  }
};
</script>