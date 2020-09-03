<template>
  <Drawer
    title="权限分配"
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
    <Button type="primary" @click="getCheckedPopedoms" long>提交</Button>
  </Drawer>
</template>
<script>
import { popedomListByRoleId } from "@/api/role";
import { MyTreeTools } from "@/libs/util";

export default {
  name: "DrawerPage",
  props: {
    value: {
      type: Boolean,
      default: false
    },
    roleId: {
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
    getPopedomListByRoleId(roleId) {
      if (roleId) {
        popedomListByRoleId({ id: roleId }).then(res => {
          this.data = new MyTreeTools().toTreeData(res, function(node) {
            node.oldChecked = node.checked
            node.title = node.text;
            node.expand = true;
            if (node.checked === "false" || node.TYPE === 1) {
              node.checked = false;
            } else {
              node.checked = true;
            }
          });
          let vm = this
          updateTreeData(this.data)
          //当菜单为根节点也被选中时
          function updateTreeData(nodeArray) {
            //debugger
            for (var i = 0; i < nodeArray.length; i++) {
              if (nodeArray[i].children.length == 0 && nodeArray[i].oldChecked == "true") {
                nodeArray[i].checked = true;
                vm.$set(nodeArray, i, nodeArray[i]);
              } else {
                updateTreeData(nodeArray[i].children);
              }
            }
          }
        });
      }
    },
    getCheckedPopedoms() {
      //获取权限ids
      let popedoms = this.$refs.tree.getCheckedAndIndeterminateNodes();
      let popedomIds = "";
      for (let i = 0; i < popedoms.length; i++) {
        popedomIds += popedoms[i].id + ",";
      }
      this.$emit("on-save-popedoms", popedomIds);
    }
  },
  watch: {
    roleId: function(val) {
      this.getPopedomListByRoleId(val);
    }
  },
  mounted() {
    this.getPopedomListByRoleId(this.roleId);
  }
};
</script>